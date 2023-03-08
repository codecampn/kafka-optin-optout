import { defineConfig } from 'orval';

export default defineConfig({
  client: {
    input: '../optin-optout/src/main/resources/api.yaml',
    output: {
      target: './src/api/opt-out.ts',
      schemas: './src/api/model',
      client: 'react-query',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
  ktable: {
    input: '../streams/src/main/resources/api.yaml',
    output: {
      target: './src/api/aggregate.ts',
      schemas: './src/api/model',
      client: 'react-query',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
  database: {
    input: './src/intern-api.yaml',
    output: {
      target: './src/api/database.ts',
      schemas: './src/api/model-database',
      client: 'react-query',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
});
