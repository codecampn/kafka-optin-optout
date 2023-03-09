import { SinkDatabase } from '@/api/model';
import type { NextApiRequest, NextApiResponse } from 'next';
import { Pool } from 'pg';

export default async function handler(
  _req: NextApiRequest,
  res: NextApiResponse<SinkDatabase>
) {

  const data = await getChannelConsents();

  // Get data from your database
  res.status(200).json(data);
}

async function getChannelConsents(): Promise<SinkDatabase> {
  const pool = new Pool({
    user: 'sinkuser',
    host: 'localhost',
    database: 'postgres',
    password: process.env.DATABASE_PASSWORD,
    port: 5432, // or your PostgreSQL port number
  });

  const query = {
    text: 'SELECT * FROM channel_consents',
  };

  try {
    const result = await pool.query(query);
    return result.rows;
  } catch (error) {
    console.error('Error executing query', error);
    throw error;
  } finally {
    pool.end(); // release the client back to the pool
  }
}
