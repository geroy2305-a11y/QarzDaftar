QARZ DAFTAR — Android Studio / GitHub Actions loyiha

Bu ZIP hali APK emas: unda APKni yig‘ish uchun loyiha va avtomatik build workflow mavjud.

KOMPYUTERDA ANDROID STUDIO ORQALI:
1. Android Studio o‘rnating (Android SDK Platform 35, Java 17).
2. ZIPni ochib QarzDaftarAndroid papkasini Android Studio’da oching.
3. Gradle Sync tugagach, Build > Build APK(s) ni tanlang.
4. APK: app/build/outputs/apk/debug/app-debug.apk

GITHUB ACTIONS ORQALI:
1. GitHub’da yangi repository oching.
2. ZIPni kompyuterda ochib, ichidagi fayllarni repository’ga yuklang (yoki git orqali push qiling).
3. GitHub repository > Actions > “Build Qarz Daftar APK” workflow’ni tanlang.
4. Run workflow tugmasini bosing (yoki main/master branch’ga push qiling).
5. Build muvaffaqiyatli bo‘lsa, workflow run sahifasidagi Artifacts qismidan “QarzDaftar-debug-apk” ni yuklab oling.
6. APKni Android telefoningizga o‘tkazing va o‘rnatish uchun “Install unknown apps” ruxsatini bering.

Muhim:
- APKni bizning hozirgi muhitda kompilyatsiya qilish uchun Android SDK mavjud emas.
- Loyiha build va real qurilma testidan hali o‘tmagan.
- Native alarm integration mavjud, Android’da bildirishnoma va exact alarm ruxsatlarini yoqish kerak bo‘lishi mumkin.
