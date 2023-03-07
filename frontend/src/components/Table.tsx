import React from 'react';

import { ChannelConsent, CustomerConsents } from '@/api/model';

interface TableProps {
  customerConsents: CustomerConsents;
}

export function Table({ customerConsents }: TableProps) {
  return (
    <div className="-my-2 -mx-4 overflow-x-auto sm:-mx-6 lg:-mx-8">
      <div className="inline-block min-w-full py-2 align-middle">
        <div className="overflow-hidden shadow-sm ring-1 ring-black ring-opacity-5">
          <table className="min-w-full divide-y divide-gray-300">
            <thead className="bg-gray-50">
              <tr>
                <th
                  scope="col"
                  className="py-3.5 pl-4 pr-3 text-left text-sm font-semibold text-gray-900 sm:pl-6 lg:pl-8"
                >
                  CustomerId
                </th>
                <th
                  scope="col"
                  className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                >
                  E-mails
                </th>
                <th
                  scope="col"
                  className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                >
                  Telephone
                </th>
                <th
                  scope="col"
                  className="px-3 py-3.5 text-left text-sm font-semibold text-gray-900"
                >
                  LastChange
                </th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200 bg-white">
              {customerConsents.map((customerConsent) => (
                <tr key={customerConsent.customerId}>
                  <td className="whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-6 lg:pl-8">
                    {customerConsent.customerId}
                  </td>
                  <td className="whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-6 lg:pl-8">
                    <ul>
                      {customerConsent.channelConsents
                        ?.filter(
                          (channelConsent) => channelConsent.channel === 'mail'
                        )
                        .map((channelConsent) => (
                          <li key={channelConsent.target}>
                            {channelConsent.target}
                          </li>
                        ))}
                    </ul>
                  </td>
                  <td className="whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-6 lg:pl-8">
                    <ul>
                      {customerConsent.channelConsents
                        ?.filter(
                          (channelConsent) => channelConsent.channel === 'phone'
                        )
                        .map((channelConsent) => (
                          <li key={channelConsent.target}>
                            {channelConsent.target}
                          </li>
                        ))}
                    </ul>
                  </td>
                  <td className="whitespace-nowrap py-4 pl-4 pr-3 text-sm font-medium text-gray-900 sm:pl-6 lg:pl-8">
                    {getLatestConsentDate(customerConsent.channelConsents)}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

function getLatestConsentDate(
  channelConsents: ChannelConsent[]
): string | null {
  const latestDate = channelConsents?.reduce(
    (latest: Date | null, current: ChannelConsent) => {
      const currentDate = new Date(current.consentDate);
      if (!latest || currentDate > latest) {
        latest = currentDate;
      }
      return latest;
    },
    null
  );

  if (latestDate) {
    return latestDate.toLocaleString('de-DE', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
    });
  }

  return null;
}
