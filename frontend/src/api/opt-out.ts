/**
 * Generated by orval v6.11.0 🍺
 * Do not edit manually.
 * Customer Info Service
 * OpenAPI spec version: 1.0.0
 */
import axios from 'axios';
import type { AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios';
import { useMutation } from '@tanstack/react-query';
import type {
  UseMutationOptions,
  MutationFunction,
} from '@tanstack/react-query';
import type { Error, GrantConsentBody, RevokeConsentBody } from './model';

/**
 * @summary Give consent for advertisment in the given channel
 */
export const grantConsent = (
  customerId: string,
  channel: string,
  grantConsentBody: GrantConsentBody,
  options?: AxiosRequestConfig
): Promise<AxiosResponse<unknown>> => {
  return axios.post(
    `/customer/${customerId}/consent/${channel}`,
    grantConsentBody,
    options
  );
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
    { customerId: string; channel: string; data: GrantConsentBody },
    TContext
  >;
  axios?: AxiosRequestConfig;
}) => {
  const { mutation: mutationOptions, axios: axiosOptions } = options ?? {};

  const mutationFn: MutationFunction<
    Awaited<ReturnType<typeof grantConsent>>,
    { customerId: string; channel: string; data: GrantConsentBody }
  > = (props) => {
    const { customerId, channel, data } = props ?? {};

    return grantConsent(customerId, channel, data, axiosOptions);
  };

  return useMutation<
    Awaited<ReturnType<typeof grantConsent>>,
    TError,
    { customerId: string; channel: string; data: GrantConsentBody },
    TContext
  >(mutationFn, mutationOptions);
};

/**
 * @summary Revoke consent for advertisment in the given channel
 */
export const revokeConsent = (
  customerId: string,
  channel: string,
  revokeConsentBody: RevokeConsentBody,
  options?: AxiosRequestConfig
): Promise<AxiosResponse<unknown>> => {
  return axios.post(
    `/customer/${customerId}/revoke/${channel}`,
    revokeConsentBody,
    options
  );
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
    { customerId: string; channel: string; data: RevokeConsentBody },
    TContext
  >;
  axios?: AxiosRequestConfig;
}) => {
  const { mutation: mutationOptions, axios: axiosOptions } = options ?? {};

  const mutationFn: MutationFunction<
    Awaited<ReturnType<typeof revokeConsent>>,
    { customerId: string; channel: string; data: RevokeConsentBody }
  > = (props) => {
    const { customerId, channel, data } = props ?? {};

    return revokeConsent(customerId, channel, data, axiosOptions);
  };

  return useMutation<
    Awaited<ReturnType<typeof revokeConsent>>,
    TError,
    { customerId: string; channel: string; data: RevokeConsentBody },
    TContext
  >(mutationFn, mutationOptions);
};
