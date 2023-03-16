import { defineConfig } from 'orval';

export default defineConfig({
  client: {
    input: '../optin-optout/src/main/resources/api.yaml',
    output: {
      target: './src/api/opt-out.ts',
      schemas: './src/api/model',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
  ktable: {
    input: '../streams/src/main/resources/api.yaml',
    output: {
      target: './src/api/ktable.ts',
      schemas: './src/api/model',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
  internal: {
    input: './src/intern-api.yaml',
    output: {
      target: './src/api/internal-api.ts',
      schemas: './src/api/model',
      client: 'react-query',
    },
    hooks: {
      afterAllFilesWrite: 'prettier --write',
    },
  },
});
