import React, { useState } from 'react';

import { useGrantConsent } from '@/api/opt-out';
import axios from 'axios';

import { FormikState } from 'formik';
import { OptInOut, OptInOutForm, OptInOutProps } from '../OptInOut';
import { EMAIL_FORM_SCHEMA } from '../schemas';

export function OptInEmail() {
  axios.defaults.baseURL = process.env.NEXT_PUBLIC_OPTINOUT_BASE_URL;
  const [optInError, setOptInError] = useState<string>();
  const [pending, setPending] = useState<boolean>(false);

  const { mutateAsync: mutateAsyncGrant } = useGrantConsent();

  const optInEmail = async (
    values: OptInOutForm,
    resetForm: (
      nextState?: Partial<FormikState<OptInOutForm>> | undefined
    ) => void
  ) => {
    setPending(true);
    mutateAsyncGrant({
      customerId: values.userId,
      channel: 'mail',
      data: { target: values.target },
    })
      .catch((e) => {
        setOptInError(e);
      })
      .finally(() => {
        setPending(false);
        resetForm();
      });
  };

  const optInEmailProps: OptInOutProps = {
    submitButtonText: 'Yes, Send me updates',
    onSubmit: optInEmail,
    schema: EMAIL_FORM_SCHEMA,
    submitInfoText:
      'By submitting you agree to our legal terms. You can unsubscribe from our newsletter anytime.',
    target: 'mail',
  };

  return (
    <OptInOut
      {...optInEmailProps}
      pending={pending}
      error={optInError}
    ></OptInOut>
  );
}
