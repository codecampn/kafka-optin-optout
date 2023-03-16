import React, { useState } from 'react';

import axios from 'axios';

import { FormikState } from 'formik';
import { OptInOut, OptInOutForm, OptInOutProps } from '../OptInOut';
import { EMAIL_FORM_SCHEMA } from '../schemas';
import { useInvokeRevokeConsent } from '@/api/internal-api';

export function OptOutEmail() {
  axios.defaults.baseURL = process.env.NEXT_PUBLIC_OPTINOUT_BASE_URL;
  const [optInError, setOptInError] = useState<string>();
  const [pending, setPending] = useState<boolean>(false);

  const { mutateAsync: mutateAsyncRevoke } = useInvokeRevokeConsent();

  const optOutEmail = async (
    values: OptInOutForm,
    resetForm: (
      nextState?: Partial<FormikState<OptInOutForm>> | undefined
    ) => void
  ) => {
    setPending(true);
    mutateAsyncRevoke({
      data: {
        target: values.target,
        customerId: values.userId,
        channel: 'mail',
      },
    })
      .catch((e) => {
        setOptInError(e);
      })
      .finally(() => {
        setPending(false);
        resetForm();
      });
  };

  const optOutEmailProps: OptInOutProps = {
    submitButtonText: 'stop sending me updates',
    onSubmit: optOutEmail,
    schema: EMAIL_FORM_SCHEMA,
    submitInfoText:
      'By submitting you agree to our legal terms. You can subscribe our newsletter again anytime.',
    target: 'mail',
  };

  return (
    <OptInOut
      {...optOutEmailProps}
      pending={pending}
      error={optInError}
    ></OptInOut>
  );
}
