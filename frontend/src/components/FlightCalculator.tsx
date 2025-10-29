import React, { useState } from 'react';
import { flightFinderApi } from '../services/api';
import { FlightFinderResponse } from '../types';

/**
 * Flight Calculator Component
 */
const FlightCalculator: React.FC = () => {
  const [inputString, setInputString] = useState<string>('');
  const [result, setResult] = useState<FlightFinderResponse | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');

  /**
   * Handle form submission
   */
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    // Clear previous results and errors
    setResult(null);
    setError('');

    // Validate input
    if (!inputString.trim()) {
      setError('Please enter a string');
      return;
    }

    if (inputString.length > 100) {
      setError('Input cannot exceed 100 characters');
      return;
    }

    if (!/^[a-z]*$/.test(inputString)) {
      setError('Only lowercase letters are allowed');
      return;
    }

    // Call API
    setLoading(true);
    try {
      const response = await flightFinderApi.calculateFlight(inputString);
      setResult(response);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred');
    } finally {
      setLoading(false);
    }
  };

  /**
   * Handle input change
   */
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setInputString(value);
    setError(''); // Clear error
  };

  /**
   * Clear form
   */
  const handleClear = () => {
    setInputString('');
    setResult(null);
    setError('');
  };

  return (
    <div className="bg-white rounded-lg shadow-md p-6 mb-6">
      <h2 className="text-2xl font-bold mb-4 text-gray-800">
        ✈️ Flight Calculator
      </h2>
      
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Input field */}
        <div>
          <label htmlFor="inputString" className="block text-sm font-medium text-gray-700 mb-2">
            Enter a string (lowercase letters only, max 100 characters):
          </label>
          <input
            type="text"
            id="inputString"
            value={inputString}
            onChange={handleInputChange}
            placeholder="e.g., flightflight"
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            disabled={loading}
            maxLength={100}
          />
          <p className="text-sm text-gray-500 mt-1">
            {inputString.length}/100 characters
          </p>
        </div>

        {/* Button group */}
        <div className="flex space-x-3">
          <button
            type="submit"
            disabled={loading || !inputString}
            className="flex-1 bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 disabled:bg-gray-400 disabled:cursor-not-allowed transition duration-200"
          >
            {loading ? 'Calculating...' : 'Calculate'}
          </button>
          <button
            type="button"
            onClick={handleClear}
            disabled={loading}
            className="px-4 py-2 border border-gray-300 rounded-md hover:bg-gray-50 disabled:bg-gray-100 disabled:cursor-not-allowed transition duration-200"
          >
            Clear
          </button>
        </div>
      </form>

      {/* Error message */}
      {error && (
        <div className="mt-4 p-4 bg-red-50 border border-red-200 rounded-md">
          <p className="text-red-600 text-sm">❌ {error}</p>
        </div>
      )}

      {/* Result display */}
      {result && (
        <div className="mt-6 p-6 bg-gradient-to-r from-blue-50 to-indigo-50 rounded-lg border border-blue-200">
          <h3 className="text-lg font-semibold text-gray-800 mb-3">Result:</h3>
          <div className="space-y-2">
            <p className="text-gray-700">
              <span className="font-medium">Input:</span>{' '}
              <code className="bg-white px-2 py-1 rounded text-sm">{result.inputString}</code>
            </p>
            <p className="text-gray-700">
              <span className="font-medium">Can form "flight":</span>{' '}
              <span className="text-3xl font-bold text-blue-600">{result.count}</span>{' '}
              <span className="text-gray-600">time{result.count !== 1 ? 's' : ''}</span>
            </p>
          </div>
        </div>
      )}
    </div>
  );
};

export default FlightCalculator;