package com.wsss.basic.thread.phaser;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class FileSearch implements Runnable {

	private String initPath;
	private String end;
	private List<String> results;

	private Phaser phaser;
	
	public static void main(String[] args) {
		Phaser phaser = new Phaser(3) {

			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				// TODO Auto-generated method stub
				System.out.println("当前阶段完成："+ phase + ";" + registeredParties);
				return super.onAdvance(phase, registeredParties);
			}
			
		};

		FileSearch system = new FileSearch("C:\\Windows", "log", phaser);
		FileSearch apps = new FileSearch("c:\\Program Files", "log", phaser);
		FileSearch documents = new FileSearch("c:\\Documents And Settings",
				"log", phaser);

		Thread systemThread = new Thread(system, "System");
		systemThread.start();

		Thread appsThread = new Thread(apps, "apps");
		appsThread.start();

		Thread documentsThread = new Thread(documents, "documents");
		documentsThread.start();

		try {
			systemThread.join();
			appsThread.join();
			documentsThread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Terminated: " + phaser.isTerminated());

	}
	
	public FileSearch(String initPath, String end, Phaser phaser) {
		this.initPath = initPath;
		this.end = end;
		this.phaser = phaser;
		this.results = new ArrayList<String>();
	}

	private void directoryProcess(File file) {
		File list[] = file.listFiles();
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					directoryProcess(list[i]);
				} else {
					fileProcess(list[i]);
				}
			}
		}
	}

	private void fileProcess(File file) {
		if (file.getName().endsWith(end)) {
			results.add(file.getAbsolutePath());
		}
	}

	private void filterResults() {
		List<String> newResults = new ArrayList<String>();
		long actualDate = new Date().getTime();
		for (int i = 0; i < results.size(); i++) {
			File file = new File(results.get(i));
			long fileDate = file.lastModified();
			if (actualDate - fileDate < TimeUnit.MILLISECONDS.convert(1,TimeUnit.DAYS)) {
				newResults.add(results.get(i));
			}
		}
		results = newResults;
	}

	private boolean checkResults() {
		if (results.isEmpty()) {
			System.out.printf("%s: Phase %d: 0 results.\n", Thread.currentThread().getName(), phaser.getPhase());
			System.out.printf("%s: Phase %d: End.\n", Thread.currentThread().getName(), phaser.getPhase());
			phaser.arriveAndDeregister();
			return false;

		} else {
			System.out.printf("%s: Phase %d: %d results.\n", Thread.currentThread().getName(), phaser.getPhase(), results.size());
			phaser.arriveAndAwaitAdvance();
			return true;
		}
	}

	private void showInfo() {
		for (int i = 0; i < results.size(); i++) {
			File file = new File(results.get(i));
			System.out.printf("%s: %s\n", Thread.currentThread().getName(),
					file.getAbsolutePath());

		}
		phaser.arriveAndAwaitAdvance();
	}

	@Override
	public void run() {
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s: Starting.\n", Thread.currentThread().getName());

		File file = new File(initPath);
		if (file.isDirectory()) {
			directoryProcess(file);
		}
		if (!checkResults()) {
			return;
		}
		filterResults();
		if (!checkResults()) {
			return;
		}
		showInfo();
		phaser.arriveAndDeregister();
		System.out.printf("%s: Work completed.\n", Thread.currentThread()
				.getName());

	}

}
