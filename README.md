# README
Spakbor Hills
Gambaran Umum
Spakbor Hills adalah permainan simulasi bertani yang dikembangkan dengan Java sebagai bagian dari mata kuliah IF2010 Pemrograman Berorientasi Objek. Permainan ini mengikuti kisah Dr. Asep Spakbor, seorang mantan penjahat yang telah memutuskan untuk memulai kehidupan baru sebagai petani di desa damai Spakbor Hills.

kalau gw buat readme nya gini di bagian run bener ga?                                                                                                                                     

# 1. Instalasi Prasyarat

# Instalasi Java Development Kit (JDK)
Unduh JDK dari situs resmi Oracle atau OpenJDK
Ikuti petunjuk instalasi untuk sistem operasi Anda
Setelah instalasi, pastikan JDK berfungsi dengan menjalankan:
java -version

# Instalasi Maven
Windows:
- Unduh Maven dari situs resmi Apache Maven
-  Ekstrak file ZIP ke lokasi permanen (misalnya C:\Maven\apache-maven-3.9.6)
- Atur variabel lingkungan:
Buka Control Panel > System > Advanced System Settings > Environment Variables
Buat variabel JAVA_HOME baru: C:\Path\To\Your\JDK (tanpa folder bin)
Buat variabel MAVEN_HOME baru: C:\Maven\apache-maven-3.9.6
Tambahkan %MAVEN_HOME%\bin ke variabel PATH
- Verifikasi instalasi dengan membuka Command Prompt baru:
mvn --version


macOS/Linux:
- Instal menggunakan package manager:
macOS: brew install maven
Ubuntu/Debian: sudo apt install maven
Fedora: sudo dnf install maven

- Atau instal secara manual:
Unduh dan ekstrak Maven
Atur variabel lingkungan di ~/.bashrc atau ~/.zshrc:
export JAVA_HOME=/path/to/your/jdk
export MAVEN_HOME=/path/to/maven
export PATH=$PATH:$MAVEN_HOME/bin

- Verifikasi instalasi:
mvn --version

# Menjalankan Proyek Spakbor Hills dengan Maven
Metode 1: Menjalankan Main.java Langsung
Jika proyek hanya terdiri dari file Main.java tunggal:
bash# Arahkan ke direktori proyek
cd path/to/spakbor-hills

# Kompilasi dengan Maven
mvn compile

# Jalankan kelas Main
mvn exec:java -Dexec.mainClass="Main"
Metode 2: Menjalankan Proyek Maven

# 2. Fitur Permainan
## Mekanik Utama
- Bertani: Membajak tanah, menanam tanaman, menyiram tanaman, dan memanen saat siap
- Memancing: Menangkap berbagai jenis ikan di lokasi yang berbeda
- Memasak: Membuat hidangan dari hasil panen dan ikan yang ditangkap
- Interaksi NPC: Mengobrol dengan penduduk desa, memberikan hadiah, dan membangun hubungan
- Sistem Waktu: Waktu dalam permainan berjalan dengan kecepatan 1 detik nyata = 5 menit dalam permainan
- Perubahan Musim: Setiap musim berlangsung 10 hari dan mempengaruhi gameplay
- Sistem Cuaca: Hari cerah dan hujan yang mempengaruhi pertanian

## Aksi Pemain
- Tilling: Mengubah land menjadi soil (-5 energi, -5 menit)
- Recover Land: Mengubah soil kembali menjadi land (-5 energi, -5 menit)
- Planting: Menanam benih di tanah yang telah dibajak (-5 energi, -5 menit)
- Watering: Menyiram tanah yang ditanami (-5 energi, -5 menit)
- Harvesting: Mengumpulkan tanaman matang (-5 energi, -5 menit)
- Eating: Mengonsumsi makanan untuk memulihkan energi
- Sleeping: Beristirahat untuk memulihkan energi dan maju ke hari berikutnya
- Cooking: Menyiapkan makanan dari bahan-bahan
- Fishing: Menangkap ikan di lokasi berbeda
- Proposing: Melamar NPC untuk menjadi tunangan
- Marrying: Menikahi tunangan
- Watching: Menonton TV untuk memeriksa cuaca
- Visiting: Mengunjungi lokasi di luar pertanian
- Chatting: Berbicara dengan NPC untuk membangun persahabatan
- Gifting: Memberikan barang kepada NPC untuk meningkatkan hubungan
- Moving: Bernavigasi di sekitar pertanian
- Inventory Management: Melihat dan mengatur barang-barang
- Selling: Menempatkan barang di shipping bin untuk dijual dan mendapatkan gold

# 3. Kontrol Permainan
## Perintah Dasar
- new = Memulai permainan baru
- help = Menampilkan informasi bantuan
- exit = Keluar dari permainan
- info = Melihat informasi pemain
- time = Memeriksa waktu, hari, musim saat ini
- location = Memeriksa lokasi saat ini
- inventory = Melihat isi inventaris

## Perintah Aksi
- move [arah] = Bergerak ke arah tertentu (utara/selatan/timur/barat)
- till = Menggunakan cangkul untuk membajak tanah
- plant [benih] = Menanam benih di tanah yang dibajak
- water = Menyiram tanah yang ditanami
- harvest = Memanen tanaman matang
- eat [item] = Mengonsumsi makanan
- sleep = Pergi tidur
- cook [resep] = Memasak resep
- fish = Pergi memancing
- chat [NPC] = Mengobrol dengan NPC
- gift [NPC] [item] = Memberikan barang kepada NPC
- visit [lokasi] = Mengunjungi lokasi
- sell [item] [jumlah] = Menempatkan barang di shipping bin

# 3. Konsep Pemrograman Berorientasi Objek yang Diimplementasikan
## 1. Inheritance (Pewarisan)
BaseItem → Hierarki Crop/Seed/Fish/Tool
BaseNPC → Implementasi NPC individu

## 2. Abstract Classes / Interfaces (Kelas Abstrak / Antarmuka)
IInteractable untuk semua objek yang dapat diinteraksi
AbstractItem sebagai dasar untuk semua jenis item
Antarmuka ActionHandler untuk semua pemroses aksi

## 3. Polymorphism (Polimorfisme)
Berbagai jenis item berperilaku berbeda saat digunakan
Reaksi NPC bervariasi berdasarkan kepribadian dan preferensi hadiah

## 4. Generics
Implementasi Inventory<T extends Item>
EventHandler<T extends Event> untuk event permainan

## 5. Exceptions (Pengecualian)
Pengecualian khusus untuk aksi yang tidak valid
Penanganan kesalahan untuk energi/sumber daya yang tidak mencukupi

6. Concurrency (Konkurensi)
Sistem manajemen waktu paralel
Pemrosesan aksi asinkron

# 4. Design Patterns yang Diimplementasikan
1. Singleton
Digunakan untuk manajemen status permainan global dan pelacakan waktu
2. Factory
Sistem pembuatan item untuk menghasilkan berbagai jenis item
3. Observer
Sistem event untuk perubahan status permainan dan notifikasi
4. Command
Sistem eksekusi aksi yang memungkinkan fungsionalitas undo/redo
5. State
Manajemen status pemain dan NPC

# 5. Tim Pengembang
- Leonard Arif S.(18223120)
- Nicholas Zefanya L.(18223111)
- Kenzie Raffa A.(18223127)
- Anggita Najmi L.(18223122)
- Maria Vransiska P.C.T.D.P.(18223119)

# 6. Referensi
## Bahasa Pemrograman dan OOP
- Oracle. (2023). The Java Tutorials. https://docs.oracle.com/javase/tutorial/
- Bloch, J. (2018). Effective Java (3rd ed.). Addison-Wesley Professional.
- Horstmann, C. S. (2019). Core Java Volume I—Fundamentals (11th ed.). Pearson.

## Design Patterns:
- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). Design Patterns: Elements of Reusable Object-Oriented Software. Addison-Wesley.
- Freeman, E., Robson, E., Bates, B., & Sierra, K. (2020). Head First Design Patterns (2nd ed.). O'Reilly Media.
- Refactoring.guru. (2023). Design Patterns. https://refactoring.guru/design-patterns
