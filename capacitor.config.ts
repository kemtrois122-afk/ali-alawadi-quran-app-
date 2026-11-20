import type { CapacitorConfig } from '@capacitor/cli';

/** Initial online wrapper. Replace server.url with a bundled web build for genuine offline support. */
const config: CapacitorConfig = {
  appId: 'com.alialawadi.quran',
  appName: 'علم ينتفع به - الدكتور علي العوضي',
  webDir: 'www',
  server: {
    url: 'https://dr-ali-alawadi-quran.higgsfield.app/',
    cleartext: false
  },
  android: { allowMixedContent: false }
};
export default config;
