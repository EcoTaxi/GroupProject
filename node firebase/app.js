

const SerialPort = require('serialport');
const admin = require('firebase-admin');
const firebase = require('firebase');

//const serviceAccount = require("C:\Users\DivideBy0FunCtioN\Desktop\node firebase\ecotaxiphoneapp-9e80f-firebase-adminsdk-vdadk-dc17177dbd.json");
SerialPort.list()
  .then(ports => {
    const port = ports.find(port => /arduino/i.test(port.manufacturer));
    if (!port) {
      console.error('Arduino Not found');
      process.exit(1);
    }
    console.log(port.comName);
  });

  process.env.DEBUG = '*';


// outputs the path to an arduino or nothing
function findArduino() {
  return new Promise((resolve, reject) => {
    if (process.argv[2]) {
      return resolve(process.argv[2]);
    }
    SerialPort.list((err, ports) => {
      if (err) { return reject(err) }
      let resolved = false;
      ports.forEach((port) => {
        if (!resolved && /arduino/i.test(port.manufacturer)) {
          resolved = true;
          return resolve(port.comName);
        }
      });
      if (!resolved) {
        reject(new Error('No arduinos found'));
      }
    });
  });
}

findArduino().then((portName) => {
  const port = new SerialPort(portName);
  port.on('open', () => {
   // console.log('opened', portName);
    
    port.on('data', data => console.log('data', data.toString())); // put the port into flowing mode
    
  });
}, () => {
  console.log('no arduino');
});

process.on('unhandledRejection', r => console.log(r, r.stack));
firebase.initializeApp({

    crendential:admin.credential.cert({
      projectId: "ecotaxiphoneapp-9e80f",
      clientEmail:"firebase-adminsdk-vdadk@ecotaxiphoneapp-9e80f.iam.gserviceaccount.com",
      privateKey:"-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzFRGcBq7yGDF0\nktOmTZlUUEmZknTO7c1iFzWcJzwVDE/Eq7cU3tuUkNvOtu2uupsFmSBDYYuj/+qM\n/FXWZH2lF8bS9T8cDhHQzACGmivJUQFsjTC4PBz5W4U3E9hEuPc1/s/RZp63EXDf\nPuMhag92S+gmUIdzMEPSe3Cjhs9Yh72PtiataKayUZUf28T0Ih1Mt9cJXyqFtP8F\nxQPMunhN1MMqfyjcqEfmbF8FCybnnjhixvSXLEYt371pUrAURZo6w1oIYQu9J/RQ\nvKqj8Z9kUsvLqvA0sBFTwV3JcvwSMvH5hESWNTS1+PYQ29LvOxGH5z5unwd1ftY5\n8ceNq/prAgMBAAECggEAKwmXL84TYS8bQ469uqvPe4nIBlZCKDDSOQ6m3Z44PD63\nNOanmyZr0up1SNqBiIpkExZXwJnCu2kXb0Z9QoboAzMRn0U9I3rmIS3FupN2jm0f\nUH0mBkC8Hdk2zKPCR9HD54ATZZmp7wkL/TrZzv/0M1POuNaFltM0mXL9AO+3COk/\nRymNNEmSLcnpstU22/CuBasG90eyNi5sk0gH9KojtLpYA1wrdDjpweuDjvptPOsk\nmxQ6kdOLPGnDwVnog+EI0Niy6qrsbFAlASGj6rkqvgLKafeYN0iO2UDnnkvhJxEy\naJn0AZgCigx7myEjp+ikrz2m5zih4dAWWSFoXcBRIQKBgQDgkMqqCe5fd18x3Vj8\n1o1smrt4cBivZ9OnZ7T6A/wTAUAGlIWBQ8478SbQlYVr+IX2odsr/uvIXbZKVaiG\nYM7/uSOGr+5DE02kkDijgJ++fBtA+H+qH5LlE8Kho2LJDOZGosnLo1LxgNSszfQy\nPBc87ZSCFYHEosxskfj1cLzbowKBgQDMJmdNu3S2OQcBj7PPBV6x9Rj/q6n++I0B\nHlyTBXeODMy0CoNgqIwPfOgaw6+IKs4+d3jXWjmVHzzJ/CJpc9pC9uNp1cThk7Sl\nW3AmA7ytAVpqmOHV2c6RBxPiKdek+mdVlgxI+XM+6mOuqO9iZcQmZQmGiIS3Qly4\nHEoQ9uvSmQKBgD5EZ62Wa15VPzQYaCBTbYt7rBfNHUslwu5cNzZL/I1Yf2RTsmER\naBH+4ABeNUym8J5eauNcIAzCz1T1Q3FfToZh05P5HFvE8nZ69vl/JMwwpW3yeZO2\nhYi3XfRr1WxiA0f6w3GPq+QDEPWJdfin6NmgFtmrFPxtF7nTD/EefX95AoGAPc76\nw9wXb/xAFMbvR6R2kSaF1RKiO2IBPu90U8b59XYGnBP6xs8A6MeGdmP8xxs+qkb9\nvSXB/VYhz32Y5Zh/4nphS+++1hUkQlQ21iuXekHIoDF9XoO9OMeNy5Gjin2Nv6nY\nOlfb/pZ0a7MWFxJ8mqtIgQTgOaPL8qoN+qjnqTECgYEAp/mc+qR/ydDq5rQAXkV9\nQHHl5At5nTutmOm9iZPGAZ2Jz4d1Xn5GsiLBLpDNTDpa8Vwz+Px9xJQCqic7YtEB\nyDhAzj1soAPRzzxGatQHoUqqIoksJuJ6yFwF3BzRC9N5wEQM8aiZiSV/c3Bs0dGY\nc86m0BihPthM16aSHG/dMgo=\n-----END PRIVATE KEY-----\n"

    }),
    databaseURL: "https://ecotaxiphoneapp-9e80f.firebaseio.com/"

});

firebase.database().ref('Car').set({
location: "The CITY"


    
});

