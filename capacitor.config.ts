import type { CapacitorConfig } from '@capacitor/cli';

/** Initial online wrapper. Replace server.url with a bundled web build for genuine offline support. */
const config: CapacitorConfig = {
  appId: 'org.alialawadi.quran',
  appName: 'علم ينتفع به - الدكتور علي العوضي',
  webDir: 'www',
  server: {
    url: 'https://dr-ali-alawadi-quran.higgsfield.app/',
    cleartext: false
  },
  android: { allowMixedContent: false }
};
export default config;
