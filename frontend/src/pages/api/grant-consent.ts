import { CustomerConsents } from '@/api/model';
import { AxiosError, AxiosResponse } from 'axios';
import { NextApiRequest, NextApiResponse } from 'next/types';
import { grantConsent  } from '@/api/opt-out';

export default async function handler(
  _req: NextApiRequest,
  res: NextApiResponse<CustomerConsents | unknown>
) {
  const {
    body: { target, channel, customerId },
  } = _req;
  if (_req.method === 'POST') {
    return grantConsent(
      customerId,
      channel,
      {
        target: target,
      },
      { baseURL: process.env.OPTINOUT_BASE_URL }
    )
      .then((response: AxiosResponse) => {
        console.log('Reponse:', response);
        return res.status(201).json({});
      })
      .catch((reason: AxiosError) => {
        console.log('Error Reason', reason);
        return res.status(Number(reason.code)).json(reason.message);
      });
  } else {
    return res.status(404).json({ message: 'Path not found' });
  }
}
