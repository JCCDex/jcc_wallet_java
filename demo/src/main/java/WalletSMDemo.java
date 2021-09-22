import com.jccdex.core.client.Wallet;
import com.jccdex.core.client.WalletSM;

/**
 * 国密版本钱包Demo
 * @author xdjiiang
 */
public class WalletSMDemo {
    public static void main(String[] args) {
        String secret;
        //------------------非国密版钱包----------------------
        //创建国密版钱包
        WalletSM wallet1 = WalletSM.generate();
        secret = wallet1.getSecret();
        System.out.println("创建国密版钱包:");
        System.out.println("钱包密钥:"+wallet1.getSecret());
        System.out.println("钱包地址:"+wallet1.getAddress());
        System.out.println("--------------------------------------------------------------");
        //通过密钥导出钱包
        WalletSM wallet2 = WalletSM.fromSecret(secret);
        System.out.println("通过密钥导出国密版钱包:");
        System.out.println("钱包密钥:"+wallet2.getSecret());
        System.out.println("钱包地址:"+wallet2.getAddress());
        System.out.println("--------------------------------------------------------------");

    }
}
