package com.wsss.frame.encipher.ed25519;

import com.cmcm.finance.sign.Signer;
import org.spongycastle.util.encoders.Hex;

public class Test {

    public static void main(String[] args) {
        signTest();
    }
    public static void signTest(){
        String pubK = "a1786ff4e3de5559357c61732423f387084db268fe9c5a85c5f8eb1a0ffb434c";
        String priK = "c1326888dd2cefd48ea9476c16fc6876ecb07ab21c4369136a7344a6f8f13025";
        try {
            String txt = "near3lusdt100000000";
            // 私钥签名
            System.out.println(txt);
            byte[] data1 = Signer.signWithHexKey(txt.getBytes(),priK);
            String sign = Hex.toHexString(data1);
            System.out.println("签名："+ sign);
            // 公钥验签
            boolean succ1 = Signer.verifyWithHexKey(txt.getBytes(),pubK, Hex.decode(sign));
            System.out.println("验签结果："+succ1);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
