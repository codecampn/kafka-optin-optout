/**
 * Generated by orval v6.11.0 🍺
 * Do not edit manually.
 * Aggregate Info Service
 * OpenAPI spec version: 1.0.0
 */
import axios from 'axios';
import type { AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios';
import { useQuery } from '@tanstack/react-query';
import type {
  UseQueryOptions,
  QueryFunction,
  UseQueryResult,
  QueryKey,
} from '@tanstack/react-query';
import type { CustomerConsents, Error } from './model';

/**
 * @summary Get all Customer Consents
 */
export const getAggregate = (
  options?: AxiosRequestConfig
): Promise<AxiosResponse<CustomerConsents>> => {
  return axios.get(`/customer-consents`, options);
};

export const getGetAggregateQueryKey = () => [`/customer-consents`];

export type GetAggregateQueryResult = NonNullable<
  Awaited<ReturnType<typeof getAggregate>>
>;
export type GetAggregateQueryError = AxiosError<Error>;

export const useGetAggregate = <
  TData = Awaited<ReturnType<typeof getAggregate>>,
  TError = AxiosError<Error>
>(options?: {
  query?: UseQueryOptions<
    Awaited<ReturnType<typeof getAggregate>>,
    TError,
    TData
  >;
  axios?: AxiosRequestConfig;
}): UseQueryResult<TData, TError> & { queryKey: QueryKey } => {
  const { query: queryOptions, axios: axiosOptions } = options ?? {};

  const queryKey = queryOptions?.queryKey ?? getGetAggregateQueryKey();

  const queryFn: QueryFunction<Awaited<ReturnType<typeof getAggregate>>> = ({
    signal,
  }) => getAggregate({ signal, ...axiosOptions });

  const query = useQuery<
    Awaited<ReturnType<typeof getAggregate>>,
    TError,
    TData
  >(queryKey, queryFn, queryOptions) as UseQueryResult<TData, TError> & {
    queryKey: QueryKey;
  };

  query.queryKey = queryKey;

  return query;
};
