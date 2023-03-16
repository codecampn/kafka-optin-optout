import React, { useState } from 'react';

import axios from 'axios';
import { PHONE_FORM_SCHEMA } from '@/components/schemas';
import { OptInOut, OptInOutForm, OptInOutProps } from '@/components/OptInOut';
import { FormikState } from 'formik';
import { useInvokeRevokeConsent } from '@/api/internal-api';

export function OptOutPhone() {
  axios.defaults.baseURL = '';
  const [optInError, setOptInError] = useState<string>();
  const [pending, setPending] = useState<boolean>(false);

  const { mutateAsync: mutateAsyncRevoke } = useInvokeRevokeConsent();

  const optOutPhone = async (
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
        channel: 'phone',
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

  const optOutPhoneProps: OptInOutProps = {
    submitButtonText: 'stop calling me',
    onSubmit: optOutPhone,
    schema: PHONE_FORM_SCHEMA,
    submitInfoText:
      'By submitting you agree to our legal terms. You can subscribe our newsletter again anytime.',
    target: 'phone',
  };

  return (
    <OptInOut
      {...optOutPhoneProps}
      pending={pending}
      error={optInError}
    ></OptInOut>
  );
}
