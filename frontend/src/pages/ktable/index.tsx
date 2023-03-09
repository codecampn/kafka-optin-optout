import React from 'react';
import { Container } from '@/components/Container';
import axios from 'axios';
import { Spinner } from '@/components/Spinner';
import { Table } from '@/components/Table';
import { useGetAggregate } from '@/api/internal-api';
export default function KTablePage() {
  axios.defaults.baseURL = '';

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
