/**
 * Generated by orval v6.11.0 🍺
 * Do not edit manually.
 * Aggregate Info Service
 * OpenAPI spec version: 1.0.0
 */
import axios from 'axios';
import type { AxiosRequestConfig, AxiosResponse } from 'axios';
import type { CustomerConsents } from './model';

/**
 * @summary Get all Customer Consents
 */
export const getKTable = <TData = AxiosResponse<CustomerConsents>>(
  options?: AxiosRequestConfig
): Promise<TData> => {
  return axios.get(`/customer-consents`, options);
};

export type GetKTableResult = AxiosResponse<CustomerConsents>;