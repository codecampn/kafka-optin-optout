import React from 'react';
import userEvent from '@testing-library/user-event';
import { render, screen } from '@testing-library/react';
import { OptInOut, OptInOutProps } from './OptInOut';
import { EMAIL_FORM_SCHEMA } from './schemas';

describe('OptInOut Component', () => {
  let user: ReturnType<(typeof userEvent)['setup']>;

  const callback = jest.fn();

  const defaultProps: OptInOutProps = {
    submitButtonText: 'Yes, Send me updates',
    onSubmit: callback,
    schema: EMAIL_FORM_SCHEMA,
    submitInfoText:
      'By submitting you agree to our legal terms. You can unsubscribe from our newsletter anytime.',
    target: 'mail',
  };

  beforeEach(() => {
    user = userEvent.setup();
  });

  it("should provide a mail import form and button for submitting in it's initial state", async () => {
    render(<OptInOut {...defaultProps}></OptInOut>);

    await getTargetnputField();
  });

  it('should display an validation error when trying to submit without an email adress', async () => {
    render(<OptInOut {...defaultProps}></OptInOut>);

    await submitSubscription();
    const error = await screen.findByTestId(
      'subscription.validation.mailRequired'
    );

    expect(error.textContent).toContain('Mail is required');
  });

  it('should call the optIn callback on submit with the provided mail adress', async () => {
    render(<OptInOut {...defaultProps}></OptInOut>);

    await enterTarget('test@test.local');
    await enterUserId('userId');
    await submitSubscription();

    expect(callback).toHaveBeenCalledWith(
      { target: 'test@test.local', userId: 'userId' },
      expect.any(Function)
    );
  });

  it('should display an error box in case an error is provided', async () => {
    render(<OptInOut {...defaultProps} error="An error occured"></OptInOut>);

    expect(screen.getByTestId('error.optin.detail').textContent).toEqual(
      'An error occured'
    );
  });

  it('should display a spinner instead of the form on pending', async () => {
    render(<OptInOut {...defaultProps} pending={true}></OptInOut>);

    expect(screen.queryByTestId('subscription.input.email')).toBeNull();
    expect(screen.queryByTestId('subscription.button.submit')).toBeNull();
    expect(screen.getByTestId('subscription.spinner')).not.toBeNull();
  });

  async function enterTarget(mail: string) {
    await user.type(await getTargetnputField(), mail);
  }

  async function enterUserId(userId: string) {
    await user.type(await getUserIdField(), userId);
  }

  async function submitSubscription() {
    await user.click(screen.getByTestId('subscription.button.submit'));
  }
});

async function getUserIdField() {
  return await screen.findByTestId('userid-input');
}

async function getTargetnputField() {
  return await screen.findByTestId('subscription.input.target');
}
