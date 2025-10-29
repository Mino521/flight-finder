import React from 'react';
import FlightCalculator from './components/FlightCalculator';
import SearchHistory from './components/SearchHistory';

/**
 * Main Application Component
 * 主应用组件
 */
function App() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-indigo-50">
      {/* Header | 页头 */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 py-6">
          <h1 className="text-4xl font-bold text-gray-900">
            ✈️ Flight Finder
          </h1>
          <p className="text-gray-600 mt-2">
            Calculate how many times "flight" can be formed from your string
          </p>
        </div>
      </header>

      {/* Main Content | 主要内容 */}
      <main className="max-w-7xl mx-auto px-4 py-8">
        <div className="space-y-8">
          {/* Calculator Section | 计算器部分 */}
          <FlightCalculator />

          {/* History Section | 历史记录部分 */}
          <SearchHistory />
        </div>
      </main>

      {/* Footer | 页脚 */}
      <footer className="bg-white border-t border-gray-200 mt-12">
        <div className="max-w-7xl mx-auto px-4 py-6 text-center text-gray-500 text-sm">
          <p>Made with ⚛️ React + TypeScript | Backend: 🍃 Spring Boot</p>
        </div>
      </footer>
    </div>
  );
}

export default App;