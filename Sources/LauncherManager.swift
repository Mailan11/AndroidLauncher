import Foundation
import UIKit

final class LauncherManager: ObservableObject {
    @Published var utmInstalled: Bool = false
    @Published var status: String = "Проверка..."
    
    private let utmScheme = "utm://"
    
    func checkUTM() {
        guard let url = URL(string: utmScheme) else { return }
        if UIApplication.shared.canOpenURL(url) {
            utmInstalled = true
            status = "UTM SE установлен ✅"
        } else {
            utmInstalled = false
            status = "UTM SE не найден. Установите из App Store"
        }
    }
    
    func launchUTM() {
        guard let url = URL(string: utmScheme) else { return }
        UIApplication.shared.open(url) { success in
            DispatchQueue.main.async {
                self.status = success ? "Открываю UTM SE..." : "Не удалось открыть UTM SE"
            }
        }
    }
    
    func openAppStore() {
        let url = URL(string: "https://apps.apple.com/app/utm-se-retro-pc-emulator/id1564628856")!
        UIApplication.shared.open(url)
    }
}