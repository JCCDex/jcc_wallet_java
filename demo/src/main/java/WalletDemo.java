import com.jccdex.core.client.Wallet;

public class WalletDemo {
    public static void main(String[] args) {
        //创建新钱包
        Wallet wallet = Wallet.generate();
        System.out.println("创建新钱包:");
        System.out.println(wallet.getSecret());
        System.out.println(wallet.getAddress());
        //通过密钥导出钱包
        Wallet wallet1 = Wallet.fromSecret("shTesjei9WvYGXceiUgSFAixakVNo");
        System.out.println("通过密钥导出钱包:");
        System.out.println(wallet1.getSecret());
        System.out.println(wallet1.getAddress());
    }
}
