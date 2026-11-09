# Modern Todo - Android Jetpack Compose & Material 3

Aplikasi To-Do List Android modern dengan arsitektur bersih (*Clean Architecture*), Jetpack Compose, Room Database, dan desain **Material 3 (Material You)**.

---

## ✨ Fitur Utama

1. **Material Design 3 (Material You):**
   - Mendukung **Dynamic Color** (Android 12+) yang beradaptasi otomatis dengan wallpaper perangkat pengguna.
   - Fallback ke palet warna modern (Indigo, Teal, Amber) dengan kontras yang nyaman di mata.
   - Dukungan penuh mode Terang (*Light Mode*) dan Gelap (*Dark Mode*).
   - Bentuk rounded modern, tipografi ekspresif M3, dan elevasi tonal (*tonal elevation*).

2. **Pengelolaan Tugas Lengkap:**
   - Tambah, edit, dan hapus tugas dengan mudah.
   - Status checklist dengan animasi transisi yang mulus (*strikethrough text animation*).
   - Pengingat tanggal jatuh tempo (*Due Date*) dengan M3 `DatePickerDialog`.
   - Deskripsi/catatan detail untuk setiap tugas.

3. **Kategori & Tag:**
   - Kategori bawaan: **Kerja (Work)**, **Pribadi (Personal)**, **Belajar (Study)**, **Belanja (Shopping)**, dan **Kesehatan (Health)**.
   - Chip filter horizontal dengan ikon kategori interaktif.

4. **Tingkat Prioritas (Priority Level):**
   - **Tinggi (High)**: Aksen merah dengan badge kontras tinggi.
   - **Sedang (Medium)**: Aksen amber/kuning.
   - **Rendah (Low)**: Aksen emerald/hijau.
   - Tugas otomatis diurutkan berdasarkan prioritas dan tanggal pembuatan.

5. **Pencarian & Filter Instan:**
   - *Real-time Search* untuk judul dan deskripsi tugas.
   - Filter cepat berdasarkan prioritas dan kategori.
   - Opsi untuk menyembunyikan atau menampilkan tugas yang sudah selesai (*Hide Completed*).

6. **Penyimpanan Lokal Persisten (Offline-First):**
   - Menggunakan **Room Database** dengan Kotlin Coroutines Flow (`TodoDao`, `TodoEntity`).
   - Perubahan data otomatis tersinkronisasi secara reaktif ke UI State tanpa reload manual.

---

## 🏛️ Arsitektur Proyek (Clean Architecture + MVI/MVVM)

```
app/src/main/java/com/example/moderntodo/
├── MainActivity.kt               # Entry point activity (Edge-to-Edge)
├── TodoApplication.kt           # Inisialisasi Database & Repository
│
├── data/                         # DATA LAYER
│   ├── local/
│   │   ├── entity/TodoEntity.kt # Room Entity
│   │   ├── TodoDao.kt           # Room Data Access Object (reactive queries)
│   │   └── TodoDatabase.kt      # Room Database singleton
│   └── repository/
│       └── TodoRepositoryImpl.kt# Implementasi TodoRepository
│
├── domain/                       # DOMAIN LAYER
│   ├── model/
│   │   ├── TodoItem.kt          # Domain data model
│   │   ├── Category.kt          # Enum kategori tugas
│   │   └── Priority.kt          # Enum level prioritas
│   └── repository/
│       └── TodoRepository.kt    # Repository contract interface
│
└── ui/                           # PRESENTATION LAYER (Jetpack Compose)
    ├── theme/
    │   ├── Color.kt             # Material 3 color tokens
    │   ├── Shape.kt             # Material 3 shape definitions
    │   ├── Type.kt              # Material 3 typography
    │   └── Theme.kt             # Dynamic Color & Theme provider
    ├── components/
    │   ├── TodoCard.kt          # Card item dengan animasi checklist & badge
    │   ├── PriorityBadge.kt     # Badge prioritas berwarna
    │   ├── CategoryChip.kt      # Bar filter kategori horizontal
    │   ├── SearchBarM3.kt       # Search bar Material 3
    │   └── EmptyState.kt        # Tampilan kosong informatif
    └── screens/
        ├── home/
        │   ├── HomeScreen.kt    # Layar utama (TopAppBar, Progress, List, FAB)
        │   ├── HomeViewModel.kt # State management & filtering logic
        │   ├── HomeUiState.kt   # Immutable UI State
        │   └── HomeUiEvent.kt   # UI Events / Intents
        └── detail/
            └── AddEditTodoSheet.kt # ModalBottomSheet tambah/edit tugas
```

---

## ⚡ Build Manual di GitHub Actions (Cloud APK Build)

Proyek ini telah dilengkapi dengan workflow CI/CD GitHub Actions: [`.github/workflows/build.yml`](.github/workflows/build.yml)

### Cara Trigger Build Manual:
1. Buka repositori Anda di GitHub.
2. Masuk ke tab **Actions** di menu atas.
3. Di panel sebelah kiri, pilih workflow **"Build Android App"**.
4. Klik tombol dropdown **"Run workflow"** di sisi kanan:
   - Pilih branch: `main`
   - Pilih opsi **Tipe Build APK**: `debug` (default), `release`, atau `both`.
   - Centang opsi lint jika ingin menjalankan pemeriksaan kode.
5. Klik tombol hijau **Run workflow**.

Setelah proses build selesai (sekitar 2–3 menit), Anda dapat langsung mengunduh file APK yang sudah jadi (`modern-todo-debug-apk.zip`) di bagian **Artifacts** pada halaman detail build tersebut, lalu install langsung ke HP Android Anda!

---

## 🚀 Cara Menjalankan Proyek Secara Lokal

1. **Buka di Android Studio:**
   - Buka Android Studio (Koala / Ladybug atau versi yang mendukung Kotlin 2.0+ & Gradle 8.6+).
   - Pilih **Open Project** dan arahkan ke folder `todo-compose-app`.
   - Biarkan Gradle melakukan sync dependensi.

2. **Jalankan pada Emulator / Perangkat Fisik:**
   - Hubungkan HP Android atau aktifkan Android Emulator (Android 8.0+ / API 26 ke atas).
   - Tekan tombol **Run** (`Shift + F10`) di toolbar Android Studio.
