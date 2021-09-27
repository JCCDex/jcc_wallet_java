# jcc_wallet_java
<!-- markdownlint-disable MD024 -->
<!-- markdownlint-disable MD033 -->
<!-- markdownlint-disable MD046 -->
<!-- markdownlint-disable MD029 -->

# 井畅钱包SDK文档(jcc-wallet-java)

## 安装和导入(maven)

```xml
   <dependency>
        <groupId>org.bouncycastle</groupId>
        <artifactId>bcprov-jdk15on</artifactId>
        <version>1.60</version>
    </dependency>
        <dependency>
            <groupId>com.jccdex</groupId>
            <artifactId>JccWallet</artifactId>
            <version>1.1.2</version>
        </dependency>
    </dependencies>
```

```java
import com.jccdex.core.client.Wallet;
```

## 钱包接口详细说明

### 1. 创建钱包(账号)
* 使用示例
 
```java
Wallet wallet = Wallet.generate();
```

### 2. 根据密钥生存钱包(账号)

* 使用示例
 
```java
Wallet wallet = Wallet.fromSecret(secret)
```

* 参数说明

   参数|类型|必填|可选值 |默认值|描述
   --|:--:|:--:|:--:|:--:|:--
   secret|String|是|-|-|钱包密钥

* 返回结果

   字段|类型|描述
   :--|:--:|:--
   -|Wallet|钱包对象

### 3. 验证钱包密钥的合法性

* 使用示例
 
```java
Wallet.isValidSecret(secret)
```

* 参数说明

   参数|类型|必填|可选值 |默认值|描述
   --|:--:|:--:|:--:|:--:|:--
   secret|String|是|-|-|钱包密钥

* 返回结果

   字段|类型|描述
   :--|:--:|:--
   -|Boolean|验证结果，合法为true,不合法为false

### 4. 验证钱包地址的合法性

* 使用示例
 
```java
Wallet.isValidAddress(address)
```

* 参数说明

   参数|类型|必填|可选值 |默认值|描述
   --|:--:|:--:|:--:|:--:|:--
   address|String|是|-|-|钱包地址

* 返回结果

   字段|类型|描述
   :--|:--:|:--
   -|Boolean|验证结果，合法为true,不合法为false

### 5. 通过私钥获得地址

* 使用示例
 
```java
Wallet wallet = Wallet.fromSecret(secret);
return wallet.getAddress();
```

* 参数说明

   参数|类型|必填|可选值 |默认值|描述
   --|:--:|:--:|:--:|:--:|:--
   secret|String|是|-|-|钱包密钥

* 返回结果

   字段|类型|描述
   :--|:--:|:--
   -|String|钱包地址,

### 6. 获得密钥

* 使用示例
 
```java
Wallet wallet = Wallet.fromSecret(secret);
return wallet.getSecret();
```

* 参数说明

   参数|类型|必填|可选值 |默认值|描述
   --|:--:|:--:|:--:|:--:|:--
   -|-|-|-|-|

* 返回结果

   字段|类型|描述
   :--|:--:|:--
   -|String|钱包密钥

### 7. 数据签名

* 使用示例
 
```java
Wallet wallet = Wallet.fromSecret(secret);
return wallet.sign(data);
```

* 参数说明

   参数|类型|必填|可选值 |默认值|描述
   --|:--:|:--:|:--:|:--:|:--
   data|String|是|-|-|数据内容

* 返回结果

   字段|类型|描述
   :--|:--:|:--
   签名内容|String|签名后的数据内容

### 8. 对签名数据进行校验

* 使用示例
 
```java
Wallet wallet = Wallet.fromSecret(secret);
return wallet.verify(data,sign);
```

* 参数说明

   参数|类型|必填|可选值 |默认值|描述
   --|:--:|:--:|:--:|:--:|:--
   data|String|是|-|-|原始数据内容
   sign|String|是|-|-|签名后的内容

* 返回结果

   字段|类型|描述
   :--|:--:|:--
   -|Boolean|验证结果，合法为true,不合法为false
