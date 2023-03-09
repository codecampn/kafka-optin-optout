/**
 * Generated by orval v6.11.0 🍺
 * Do not edit manually.
 * Database Info Service
 * OpenAPI spec version: 1.0.0
 */
import axios from 'axios';
import type { AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios';
import { useQuery, useMutation } from '@tanstack/react-query';
import type {
  UseQueryOptions,
  UseMutationOptions,
  QueryFunction,
  MutationFunction,
  UseQueryResult,
  QueryKey,
} from '@tanstack/react-query';
import type {
  SinkDatabase,
  Error,
  CustomerConsents,
  GrantConsentBody,
  RevokeConsentBody,
} from './model';

/**
 * @summary Get customer consents
 */
export const getSinkDatabase = (
  options?: AxiosRequestConfig
): Promise<AxiosResponse<SinkDatabase>> => {
  return axios.get(`/api/database`, options);
};

export const getGetSinkDatabaseQueryKey = () => [`/api/database`];

export type GetSinkDatabaseQueryResult = NonNullable<
  Awaited<ReturnType<typeof getSinkDatabase>>
>;
export type GetSinkDatabaseQueryError = AxiosError<Error>;

export const useGetSinkDatabase = <
  TData = Awaited<ReturnType<typeof getSinkDatabase>>,
  TError = AxiosError<Error>
>(options?: {
  query?: UseQueryOptions<
    Awaited<ReturnType<typeof getSinkDatabase>>,
    TError,
    TData
  >;
  axios?: AxiosRequestConfig;
}): UseQueryResult<TData, TError> & { queryKey: QueryKey } => {
  const { query: queryOptions, axios: axiosOptions } = options ?? {};

  const queryKey = queryOptions?.queryKey ?? getGetSinkDatabaseQueryKey();

  const queryFn: QueryFunction<Awaited<ReturnType<typeof getSinkDatabase>>> = ({
    signal,
  }) => getSinkDatabase({ signal, ...axiosOptions });

  const query = useQuery<
    Awaited<ReturnType<typeof getSinkDatabase>>,
    TError,
    TData
  >(queryKey, queryFn, queryOptions) as UseQueryResult<TData, TError> & {
    queryKey: QueryKey;
  };

  query.queryKey = queryKey;

  return query;
};

/**
 * @summary Get all Customer Consents
 */
export const getAggregate = (
  options?: AxiosRequestConfig
): Promise<AxiosResponse<CustomerConsents>> => {
  return axios.get(`/api/ktable`, options);
};

export const getGetAggregateQueryKey = () => [`/api/ktable`];

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

/**
 * @summary Give consent for advertisment in the given channel
 */
export const grantConsent = (
  grantConsentBody: GrantConsentBody,
  options?: AxiosRequestConfig
): Promise<AxiosResponse<unknown>> => {
  return axios.post(`/api/grant-consent`, grantConsentBody, options);
};

export type GrantConsentMutationResult = NonNullable<
  Awaited<ReturnType<typeof grantConsent>>
>;
export type GrantConsentMutationBody = GrantConsentBody;
export type GrantConsentMutationError = AxiosError<Error>;

export const useGrantConsent = <
  TError = AxiosError<Error>,
  TContext = unknown
>(options?: {
  mutation?: UseMutationOptions<
    Awaited<ReturnType<typeof grantConsent>>,
    TError,
    { data: GrantConsentBody },
    TContext
  >;
  axios?: AxiosRequestConfig;
}) => {
  const { mutation: mutationOptions, axios: axiosOptions } = options ?? {};

  const mutationFn: MutationFunction<
    Awaited<ReturnType<typeof grantConsent>>,
    { data: GrantConsentBody }
  > = (props) => {
    const { data } = props ?? {};

    return grantConsent(data, axiosOptions);
  };

  return useMutation<
    Awaited<ReturnType<typeof grantConsent>>,
    TError,
    { data: GrantConsentBody },
    TContext
  >(mutationFn, mutationOptions);
};

/**
 * @summary Give consent for advertisment in the given channel
 */
export const revokeConsent = (
  revokeConsentBody: RevokeConsentBody,
  options?: AxiosRequestConfig
): Promise<AxiosResponse<unknown>> => {
  return axios.post(`/api/revoke-consent`, revokeConsentBody, options);
};

export type RevokeConsentMutationResult = NonNullable<
  Awaited<ReturnType<typeof revokeConsent>>
>;
export type RevokeConsentMutationBody = RevokeConsentBody;
export type RevokeConsentMutationError = AxiosError<Error>;

export const useRevokeConsent = <
  TError = AxiosError<Error>,
  TContext = unknown
>(options?: {
  mutation?: UseMutationOptions<
    Awaited<ReturnType<typeof revokeConsent>>,
    TError,
    { data: RevokeConsentBody },
    TContext
  >;
  axios?: AxiosRequestConfig;
}) => {
  const { mutation: mutationOptions, axios: axiosOptions } = options ?? {};

  const mutationFn: MutationFunction<
    Awaited<ReturnType<typeof revokeConsent>>,
    { data: RevokeConsentBody }
  > = (props) => {
    const { data } = props ?? {};

    return revokeConsent(data, axiosOptions);
  };

  return useMutation<
    Awaited<ReturnType<typeof revokeConsent>>,
    TError,
    { data: RevokeConsentBody },
    TContext
  >(mutationFn, mutationOptions);
};
