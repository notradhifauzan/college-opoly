const fs = require('fs');
const pako = require('pako');
const { exec } = require('child_process');

const file = process.argv[2];
if (!file) {
  console.error('Usage: node openMermaid.js <file.mmd>');
  process.exit(1);
}

const code = fs.readFileSync(file, 'utf8');
const compressed = pako.deflate(code, { to: 'string' });
const b64 = Buffer.from(compressed).toString('base64');

const url = `https://mermaid.live/edit#pako:${b64}`;
exec(`start ${url}`); // Windows only
