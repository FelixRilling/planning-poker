/// <reference types="vitest" />

import react from "@vitejs/plugin-react";
import { readFileSync } from "fs";
import { defineConfig, UserConfig } from "vite";

// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {
  const config: UserConfig = {
    plugins: [react()],
    build: {
      outDir: "./.local/vite-build",
      rollupOptions: {
        input: "./web-app/main.tsx",
        output: {
          // Remove hashes from file name, so we have an easier time including them
          entryFileNames: "[name].js",
          assetFileNames: "[name][extname]",
        },
      },
    },
    test: {
      environment: "jsdom",
      setupFiles: "./web-app/test/vitest.setup.ts",
      include: ["./web-app/**/*.test.{ts,tsx}"],
      coverage: {
        provider: "v8",
        reporter: ["lcovonly"],
        reportsDirectory: "./.local/coverage",
      },
    },
  };

  // Only read SSL related files if needed.
  if (command == "serve" && mode != "test") {
    config.server = {
      host: "127.0.0.1",
      https: {
        key: readFileSync("./.local/ssl.key"),
        cert: readFileSync("./.local/ssl.cer"),
      },
    };
  }

  return config;
});
