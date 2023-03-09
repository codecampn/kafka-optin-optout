import { CustomerConsents } from '@/api/model';
import { AxiosError,  AxiosResponse } from 'axios';
import { NextApiRequest, NextApiResponse } from 'next/types';
import axios from 'axios';

export default async function handler(
  _req: NextApiRequest,
  res: NextApiResponse<CustomerConsents | unknown>
) {
  return axios
    .get(`http://localhost:8081/customer-consents`)
    .then((response: AxiosResponse) => {
      console.log('Response', response);
      return res.status(200).json(response.data);
    })
    .catch((reason: AxiosError) => {
      console.log('Error Reason', reason);
      return res.status(Number(reason.code)).json(reason.message);
    });
}
