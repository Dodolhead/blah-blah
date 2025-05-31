# Tugas Besar IF2010 Object Oriented Programming
> K-06 | Kelompok 4

![image](https://github.com/user-attachments/assets/388d54d2-18a2-4f7e-9986-b64b5639557f)

## Description
Stressdew Valley adalah permainan simulasi bertani yang dikembangkan dengan Java berbasis GUI Java-Swing yang dirancang untuk menjalankan actions utama bertani: tilling, planting, watering, dan harvesting. Permainan ini juga mendukung actions lain dalam berbagai map yang tersedia.

## Contributors
|          **Nama**        |  **NIM** |   **Github**   |
|--------------------------|----------|----------------|
| Nicholas Zefanya Lamtyo  | 18223111 | [@nicholaszef](https://github.com/nicholaszef)|
| Maria V.P.C.T.D.P        | 18223119 | [@MariaVransiska](http://github.com/MariaVransiska)|
| Leonard Arif Sutiono     | 18223120 | [@LeonArif](https://github.com/LeonArif)|
| Anggita Najmi Layali     | 18223122 | [@gitaa001](https://github.com/gitaa001)|
| Kenzie Raffa Ardhana     | 18223127 | [@KenzieRafa](https://github.com/KenzieRafa)|
> **Asisten:** Fawwaz Abrial Saffa (18221067)

## How to Run
1. Pastikan Java sudah terinstal dengan baik
2. Pastikan Maven sudah terinstal dengan baik
3. Clone project Stressdew-Valley.
   ```bash
      git clone https://github.com/LeonArif/Stressdew-Valley.git
   ```
4. Buka project menggunakan IDE seperti Visual Studio Code yang sudah terintegrasi dengan Maven.
5. Jalankan project pada root direktori yaitu folder Stressdew-Valley, pastikan memuat src, target, dan pom.xml
   ```bash
      mvn clean compile exec:java
   ```

## Features
Technical Features: GUI, mouse input, and keyboard utilization

Game Features:
- View Player Info: menampilkan informasi utama Player
- Farming: tilling, watering, planting, harvesting, recovering
- Fishing: permainan menebak angka
- Cooking: Membuat hidangan dari item pada inventory dan recipe
- Shipping Bin: menjual item dalam inventory untuk memperoleh gold
- NPC interact: chatting, gifting, proposing, marrying
- Time: Waktu dalam permainan berjalan dengan kecepatan 1 detik nyata = 5 menit dalam permainan
- Season: Setiap musim berlangsung 10 hari dan mempengaruhi gameplay
- Weather: Hari cerah dan hujan yang mempengaruhi pertanian

## Tech Stacks:
Visual Studio Code, Java, Java Swing



