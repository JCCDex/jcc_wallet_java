import com.jccdex.core.client.Wallet;
import com.jccdex.core.client.WalletSM;

/**
 * 非国密版本钱包Demo
 * @author xdjiiang
 */
public class WalletDemo {
    public static void main(String[] args) {
        //------------------非国密版钱包----------------------
        //创建公链新钱包
        Wallet wallet1 = Wallet.generate();
        System.out.println("创建公链钱包:");
        System.out.println("钱包密钥:"+wallet1.getSecret());
        System.out.println("钱包地址:"+wallet1.getAddress());
        System.out.println("--------------------------------------------------------------");
        //通过密钥导出钱包
        Wallet wallet2 = Wallet.fromSecret("shTesjei9WvYGXceiUgSFAixakVNo");
        System.out.println("通过密钥导出公链钱包:");
        System.out.println("钱包密钥:"+wallet2.getSecret());
        System.out.println("钱包地址:"+wallet2.getAddress());
        System.out.println("--------------------------------------------------------------");
        //创建联盟链钱包
        Wallet wallet3= Wallet.generate("dpshnaf39wBUDNEGHJKLM4PQRST7VWXYZ2bcjeCg65rkm8oFqi1tuvAxyz");
        System.out.println("创建联盟链(SEAA)钱包:");
        System.out.println("钱包密钥:"+wallet3.getSecret());
        System.out.println("钱包地址:"+wallet3.getAddress());
        System.out.println("--------------------------------------------------------------");
    }
}
