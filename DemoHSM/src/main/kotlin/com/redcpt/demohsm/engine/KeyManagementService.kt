package com.redcpt.demohsm.engine

import com.amazonaws.encryptionsdk.AwsCrypto
import com.amazonaws.encryptionsdk.CommitmentPolicy
import com.amazonaws.encryptionsdk.CryptoResult
import java.nio.charset.StandardCharsets
import java.util.*
import com.amazonaws.encryptionsdk.kms.KmsMasterKey
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider
import org.apache.log4j.Logger

object KeyManagementService {
    private val logger = Logger.getLogger(KeyManagementService::class.java)
    fun encryptAndDecryptSample(keyArn: String?, plainMessage: ByteArray?, workerKMS: String): ByteArray {
        // 1. Instantiate the SDK
        // This builds the AwsCrypto client with the RequireEncryptRequireDecrypt commitment policy,
        // which enforces that this client only encrypts using committing algorithm suites and enforces
        // that this client will only decrypt encrypted messages that were created with a committing algorithm suite.
        // This is the default commitment policy if you build the client with `AwsCrypto.builder().build()`
        // or `AwsCrypto.standard()`.
        val crypto: AwsCrypto = AwsCrypto.builder()
            .withCommitmentPolicy(CommitmentPolicy.RequireEncryptRequireDecrypt)
            .build()

        // 2. Instantiate an AWS KMS master key provider in strict mode using buildStrict().
        // In strict mode, the AWS KMS master key provider encrypts and decrypts only by using the key
        // indicated by keyArn.
        // To encrypt and decrypt with this master key provider, use an AWS KMS key ARN to identify the CMKs.
        // In strict mode, the decrypt operation requires a key ARN.
        val keyProvider: KmsMasterKeyProvider = KmsMasterKeyProvider.builder().buildStrict(keyArn)

        // 3. Create an encryption context
        // Most encrypted data should have an associated encryption context
        // to protect integrity. This sample uses placeholder values.
        // For more information see:
        // blogs.aws.amazon.com/security/post/Tx2LZ6WBJJANTNW/How-to-Protect-the-Integrity-of-Your-Encrypted-Data-by-Using-AWS-Key-Management
        val encryptionContext = Collections.singletonMap("ExampleContextKey", "ExampleContextValue")

        // 4. Encrypt the data
        val encryptResult: CryptoResult<ByteArray, KmsMasterKey> =
            crypto.encryptData(keyProvider, plainMessage, encryptionContext)
        val ciphertext: ByteArray = encryptResult.result
        logger.info(workerKMS + "Cipher Text in Byte = " + encryptResult.result)
        val s = String(encryptResult.result, StandardCharsets.UTF_8)
        logger.info(workerKMS + "Cipher Text in String = " + s)

        // 5. Decrypt the data
        val decryptResult: CryptoResult<ByteArray, KmsMasterKey> = crypto.decryptData(keyProvider, ciphertext)
        logger.info(workerKMS + "Decrypt Text in Byte = " + decryptResult.result)
        val s2 = String(decryptResult.result, StandardCharsets.UTF_8)
        logger.info(workerKMS + "Decrypt Text in String = " + s2)

        // 6. Verify that the encryption context in the result contains the
        // encryption context supplied to the encryptData method. Because the
        // SDK can add values to the encryption context, don't require that
        // the entire context matches.
        check(encryptionContext.entries.stream()
            .allMatch { (key, value): Map.Entry<String, String> ->
                value == decryptResult.encryptionContext[key]
            }) { "Wrong Encryption Context!" }
        assert(Arrays.equals(decryptResult.result, plainMessage))
        return ciphertext
    }
}