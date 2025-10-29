/**
 * Type definitions for Flight Finder application
 */

// Calculate request
export interface FlightFinderRequest {
  inputString: string;
}

// Calculate response
export interface FlightFinderResponse {
  inputString: string;
  count: number;
  message: string;
}

// Search history record
export interface SearchHistoryItem {
  id: number;
  inputString: string;
  resultCount: number;
  createdAt: string;
}

// API error response
export interface ApiError {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path?: string;
}