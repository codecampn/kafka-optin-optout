import React from 'react';
import { useGetAggregate } from '@/api/aggregate';
import { Container } from '@/components/Container';
import axios from 'axios';
import { Spinner } from '@/components/Spinner';
import { Table } from '@/components/Table';
export default function KTablePage() {
  axios.defaults.baseURL = process.env.NEXT_PUBLIC_KTABLE_BASE_URL;

  const { data, isLoading, isError } = useGetAggregate();
  if (isLoading) {
    return <Spinner />;
  }

  if (isError) {
    return <p>Error</p>;
  }
  return (
    <Container className="mx-24 mt-12">
      <Table key="k-table" customerConsents={data.data} />
    </Container>
  );
}

