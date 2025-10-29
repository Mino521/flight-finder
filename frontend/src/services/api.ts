import axios, { AxiosError } from 'axios';
import {
  FlightFinderRequest,
  FlightFinderResponse,
  SearchHistoryItem,
  ApiError
} from '../types';

/**
 * Base URL for API
 */
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api/flight-finder';

/**
 * Axios instance with default configuration
 */
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000, // 10 seconds
});

/**
 * API service for Flight Finder
 */
export const flightFinderApi = {
  /**
   * Calculate how many times "flight" can be formed
   */
  calculateFlight: async (inputString: string): Promise<FlightFinderResponse> => {
    try {
      const request: FlightFinderRequest = { inputString };
      const response = await apiClient.post<FlightFinderResponse>('/calculate', request);
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  },

  /**
   * Get all search history
   */
  getSearchHistory: async (): Promise<SearchHistoryItem[]> => {
    try {
      const response = await apiClient.get<SearchHistoryItem[]>('/history');
      return response.data;
    } catch (error) {
      throw handleApiError(error);
    }
  }
};

/**
 * Handle API errors
 */
function handleApiError(error: unknown): Error {
  if (axios.isAxiosError(error)) {
    const axiosError = error as AxiosError<ApiError>;
    
    if (axiosError.response) {
      // Server returned error response
      const apiError = axiosError.response.data;
      return new Error(apiError.message || 'An error occurred');
    } else if (axiosError.request) {
      // Request sent but no response
      return new Error('Cannot connect to server. Please check if the backend is running.');
    }
  }
  
  // Other errors
  return new Error('An unexpected error occurred');
}

export default flightFinderApi;