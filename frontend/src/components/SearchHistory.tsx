import React, { useState, useEffect } from 'react';
import { flightFinderApi } from '../services/api';
import { SearchHistoryItem } from '../types';

/**
 * Search History Component
 */
const SearchHistory: React.FC = () => {
  const [history, setHistory] = useState<SearchHistoryItem[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>('');

  /**
   * Fetch search history
   */
  const fetchHistory = async () => {
    setLoading(true);
    setError('');
    
    try {
      const data = await flightFinderApi.getSearchHistory();
      setHistory(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load history');
    } finally {
      setLoading(false);
    }
  };

  /**
   * Load history on component mount
   */
  useEffect(() => {
    fetchHistory();
  }, []);

  /**
   * Format date/time
   */
  const formatDateTime = (dateString: string): string => {
    const date = new Date(dateString);
    return date.toLocaleString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  return (
    <div className="bg-white rounded-lg shadow-md p-6">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-2xl font-bold text-gray-800">
          📜 Search History
        </h2>
        <button
          onClick={fetchHistory}
          disabled={loading}
          className="px-4 py-2 text-sm bg-gray-100 hover:bg-gray-200 rounded-md transition duration-200 disabled:bg-gray-50"
        >
          {loading ? '🔄 Refreshing...' : '🔄 Refresh'}
        </button>
      </div>

      {/* Error message */}
      {error && (
        <div className="p-4 bg-red-50 border border-red-200 rounded-md mb-4">
          <p className="text-red-600 text-sm">❌ {error}</p>
        </div>
      )}

      {/* Loading */}
      {loading && history.length === 0 && (
        <div className="text-center py-8">
          <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
          <p className="text-gray-500 mt-2">Loading history...</p>
        </div>
      )}

      {/* Empty state */}
      {!loading && history.length === 0 && (
        <div className="text-center py-8">
          <p className="text-gray-500 text-lg">No search history yet</p>
          <p className="text-gray-400 text-sm mt-2">Start calculating to see your history here</p>
        </div>
      )}

      {/*  History list */}
      {history.length > 0 && (
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Input
                </th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Result
                </th>
                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Date & Time
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-200">
              {history.map((item) => (
                <tr key={item.id} className="hover:bg-gray-50 transition duration-150">
                  <td className="px-4 py-3 whitespace-nowrap">
                    <code className="text-sm bg-gray-100 px-2 py-1 rounded">
                      {item.inputString}
                    </code>
                  </td>
                  <td className="px-4 py-3 whitespace-nowrap">
                    <span className="text-lg font-semibold text-blue-600">
                      {item.resultCount}
                    </span>
                  </td>
                  <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-500">
                    {formatDateTime(item.createdAt)}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Statistics */}
      {history.length > 0 && (
        <div className="mt-4 pt-4 border-t border-gray-200">
          <p className="text-sm text-gray-600">
            Total searches: <span className="font-semibold">{history.length}</span>
          </p>
        </div>
      )}
    </div>
  );
};

export default SearchHistory;