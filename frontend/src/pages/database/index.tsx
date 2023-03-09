import React from 'react';
import { Container } from '@/components/Container';
import axios from 'axios';
import { Table } from '@/components/Table';
import { Spinner } from '@/components/Spinner';
import { ChannelConsent, CustomerConsent } from '@/api/model';
import { SinkDatabase } from '@/api/model-database/sinkDatabase';
import { useGetSinkDatabase } from '@/api/internal-api';
export default function Overview() {
  axios.defaults.baseURL = '';

  const { data, isLoading, isError } = useGetSinkDatabase();
  if (isLoading) {
    return <Spinner />;
  }

  if (isError) {
    return <p>Error + {isError}</p>;
  }
  return (
    <Container className="mx-24 mt-12">
      <Table
        key="database"
        customerConsents={mapToCustomerConsents(data.data)}
      />
    </Container>
  );
}

function mapToCustomerConsents(data: SinkDatabase): CustomerConsent[] {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const groupedData = data.reduce((acc: { [key: string]: any }, current) => {
    const { customerId, channel, target } = current;
    const channelConsent: ChannelConsent = {
      channel,
      target,
      source: current.source,
      consentDate: current.time,
    };
    if (!acc[customerId]) {
      acc[customerId] = {
        customerId,
        channelConsents: [channelConsent],
      };
    } else {
      acc[customerId].channelConsents = [
        ...acc[customerId].channelConsents,
        channelConsent,
      ];
    }
    return acc;
  }, {});

  return Object.values(groupedData);
}
