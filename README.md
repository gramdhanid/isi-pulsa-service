# DansApps - Sistem Penjualan Pulsa dengan OTP & Notifikasi

## 📌 Deskripsi
**DansApps** adalah aplikasi penjualan pulsa yang dilengkapi fitur autentikasi OTP, integrasi sistem pihak ketiga, dan notifikasi status transaksi secara real-time.


## 🚀 Fitur Utama
1. **Registrasi User**
    - ✅ Input: username unik, nama lengkap, nomor HP, alamat, foto profil, password.
    - ✅ Validasi username.
    - ✅ Penyimpanan data di database.

2. **Login dengan OTP**
    - ✅ Login menggunakan username + password.
    - ⏳ Login 2FA.

3. **Penjualan Pulsa**
    - ✅ Input nomor HP pelanggan + nominal pulsa.
    - ⏳ Proses transaksi menggunakan message queue (Kafka/RabbitMQ).
    - ✅ Integrasi ke *Recharge System* pihak ketiga.
    - ✅ Callback dari Recharge System untuk update status transaksi.

4. **History Penjualan**
    - ✅ Tampikkan status penjualan (DIPROSES / SUKSES / GAGAL)

5. **Notifikasi Konsumen**
    - ⏳ Notifikasi otomatis ke pelanggan saat status transaksi berubah.

