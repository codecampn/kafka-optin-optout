import React from 'react';
import { Field, Form, Formik, ErrorMessage, FormikState } from 'formik';
import { Spinner } from './Spinner';
import { Error } from './Error';
import { BaseSchema } from './schemas';

export interface OptInOutForm {
  userId: string;
  target: string;
}

export const OptInOut = (props: OptInOutProps) => {
  return (
    <div className="container mx-auto">
      {props.error && (
        <Error
          scope="optin"
          title="Something went wrong"
          description={props.error}
        ></Error>
      )}
      <Formik<OptInOutForm>
        initialValues={{ userId: '', target: '' }}
        onSubmit={(values, { resetForm }) => props.onSubmit(values, resetForm)}
        validationSchema={props.schema}
      >
        <Form>
          {props.pending ? (
            <Spinner />
          ) : (
            <>
              <div className="mb-4 w-full">
                <label
                  className="mb-2 block text-sm font-bold text-gray-700"
                  htmlFor="userId"
                >
                  UserId
                </label>
                <Field
                  className="focus:shadow-outline w-full appearance-none rounded border px-3 py-2 text-sm leading-tight text-gray-700 shadow focus:outline-none"
                  type="text"
                  name="userId"
                  data-testid="userid-input"
                  placeholder="Enter ur UserId ..."
                />
                <ErrorMessage
                  name="userId"
                  render={(error) => (
                    <div className="text-center text-red-500">{error}</div>
                  )}
                />
              </div>

              <div className="mb-4 w-full">
                <label
                  className="mb-2 block text-sm font-bold text-gray-700"
                  htmlFor="target"
                >
                  {props.target === 'mail' ? 'Email' : 'Phone'}
                </label>
                <Field
                  className="focus:shadow-outline w-full appearance-none rounded border px-3 py-2 text-sm leading-tight text-gray-700 shadow focus:outline-none"
                  type={props.target === 'mail' ? 'email' : 'text'}
                  name="target"
                  data-testid="subscription.input.target"
                  placeholder={
                    props.target === 'mail'
                      ? 'Enter Email Address...'
                      : 'Enter Phone Number ...'
                  }
                />
                <ErrorMessage
                  name="target"
                  render={(error) => (
                    <div
                      className="text-center text-red-500"
                      data-testid="subscription.validation.mailRequired"
                    >
                      {error}
                    </div>
                  )}
                />
              </div>

              <div className="mb-6 text-center">
                <button
                  className="focus:shadow-outline w-full rounded-full bg-red-500 px-4 py-2 font-bold text-white hover:bg-red-700 focus:outline-none"
                  data-testid="subscription.button.submit"
                  type="submit"
                >
                  {props.submitButtonText}
                </button>
              </div>
              <hr className="mb-6 border-t" />
              <div className="text-center">{props.submitInfoText}</div>
            </>
          )}
        </Form>
      </Formik>
    </div>
  );
};

export interface OptInOutProps {
  onSubmit: {
    (
      values: OptInOutForm,
      resetForm: (
        nextState?: Partial<FormikState<OptInOutForm>> | undefined
      ) => void
    ): void;
  };
  pending?: boolean;
  error?: string;
  schema: BaseSchema;
  submitButtonText: string;
  submitInfoText: string;
  target: 'mail' | 'phone';
}
