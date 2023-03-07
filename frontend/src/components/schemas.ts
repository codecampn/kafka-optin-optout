import * as Yup from 'yup';

export type BaseSchema = Yup.SchemaOf<{
  userId: string;
  target: string;
}>;

export const EMAIL_FORM_SCHEMA: BaseSchema = Yup.object({
  userId: Yup.string().required('UserId is required'),
  target: Yup.string()
    .email('Invalid Email Address')
    .required('Mail is required'),
});

export const PHONE_FORM_SCHEMA: BaseSchema = Yup.object({
  userId: Yup.string().required('UserId is required'),
  target: Yup.string().required('Phone is required'), //TODO
});
