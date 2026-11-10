import SwiftUI

struct ContentView: View {
    @StateObject private var manager = LauncherManager()
    
    var body: some View {
        VStack(spacing: 24) {
            Spacer()
            
            Image(systemName: "play.circle.fill")
                .font(.system(size: 100))
                .foregroundStyle(.blue)
            
            Text("iOS Launcher")
                .font(.largeTitle.bold())
            
            Text(manager.status)
                .font(.subheadline)
                .foregroundStyle(.secondary)
                .multilineTextAlignment(.center)
                .padding(.horizontal)
            
            Spacer()
            
            VStack(spacing: 16) {
                Button(action: manager.checkUTM) {
                    Label("Проверить эмулятор", systemImage: "magnifyingglass")
                        .font(.headline)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.blue)
                        .foregroundStyle(.white)
                        .clipShape(RoundedRectangle(cornerRadius: 14))
                }
                
                Button(action: manager.openAppStore) {
                    Label("Установить UTM SE", systemImage: "arrow.down.app")
                        .font(.headline)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.gray.opacity(0.2))
                        .foregroundStyle(.primary)
                        .clipShape(RoundedRectangle(cornerRadius: 14))
                }
                
                Button(action: manager.launchUTM) {
                    Label("Запустить эмулятор", systemImage: "play.fill")
                        .font(.headline)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(manager.utmInstalled ? Color.green : Color.green.opacity(0.5))
                        .foregroundStyle(.white)
                        .clipShape(RoundedRectangle(cornerRadius: 14))
                }
                .disabled(!manager.utmInstalled)
            }
            .padding(.horizontal)
            .padding(.bottom, 40)
        }
    }
}