package by.terminal.posterminalandroidklient.data.repositories

import by.terminal.posterminalandroidklient.data.remote.dto.response.FreshHMACDto

internal object TestData {
    internal const val MERCHANT_ID = "eyJhbGciOiJIUzUxMiJ9eyJzdWIiOiJva29kaUBtYWlsLnJ1IiwiaWF0IjoxNzU0NTQ3OTY4L"
    internal var HMAC_KEY = FreshHMACDto(
        hmacKey = "98bd9386feb289c17a5348fc328791db24cfecdaa80950b1a47c46e4e4d2dc13",
        count = 1
    )
    internal const val RSA_PUBLIC_KEY = """-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoE664CuXHxQg0BMqPU/b
Kv9vh0YqjclSDfkQfxJCA7hVC+O4nKrRHQP4h7Ui1cQkncEI8H0Y/1ROzHLi3Iz0
e3u32kDaoSKyd4OfwGTCDE8dDcfXBEkVaft32P4yuNTydnMAVO8rJsBQOGyFuk7U
8n8BPPEwjcZwn5vkN8bvpiX8qdzHSJVUs43lbV2L+IM7oHdUmp57WL+Lr7AUJNQ5
2g2Uz5C6kiRMuRZSMWFJlGzo5wc4LSAi2x7lX3MN40nBX4RZdOV2riKWVQkOwX2f
eAEPSlMgxE6A03LR0grpdWiiFfhHzwUSNtRcmPfRhH11RfI03vnpRfH/vAqtIfy8
JQIDAQAB
-----END PUBLIC KEY-----"""
}