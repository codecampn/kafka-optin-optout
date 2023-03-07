import React, { useState } from 'react';

import { useGrantConsent } from '../../api/opt-out';
import axios from 'axios';

import { FormikState } from 'formik';
import { OptInOut, OptInOutForm, OptInOutProps } from '../OptInOut';
import { PHONE_FORM_SCHEMA } from '../schemas';

export function OptInPhone() {
  axios.defaults.baseURL = process.env.NEXT_PUBLIC_OPTINOUT_BASE_URL;
  const [optInError, setOptInError] = useState<string>();
  const [pending, setPending] = useState<boolean>(false);

  const { mutateAsync: mutateAsyncGrant } = useGrantConsent();

  const optInPhone = async (
    values: OptInOutForm,
    resetForm: (
      nextState?: Partial<FormikState<OptInOutForm>> | undefined
    ) => void
  ) => {
    setPending(true);
    mutateAsyncGrant({
      customerId: values.userId,
      channel: 'phone',
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

  const optInPhonelProps: OptInOutProps = {
    submitButtonText: 'Yes, Call me pls',
    onSubmit: optInPhone,
    schema: PHONE_FORM_SCHEMA,
    submitInfoText:
      'By submitting you agree to our legal terms. You can unsubscribe from our newsletter anytime.',
    target: 'phone',
  };

  return (
    <OptInOut
      {...optInPhonelProps}
      pending={pending}
      error={optInError}
    ></OptInOut>
  );
}
