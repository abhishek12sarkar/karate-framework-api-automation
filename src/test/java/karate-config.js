function fn() {

    const passwordDecryptor = Java.type('java.util.Base64');
    const javaString = Java.type('java.lang.String');

    karate.configure('headers',
        {
            merchantId: '12345',
            millisecondsToWait: 30000
        });

    const encryptedPassword = 'U0pLVzI2TE00UldCUkw1SDZHVDFMREZDSEM';
    karate.configure('ssl', {
        keyStore: 'classpath:BHN_API_AUTH.p12',
        keyStorePassword: new javaString(passwordDecryptor.getDecoder().decode(encryptedPassword)),
        keyStoreType: 'pkcs12'
    });

    karate.configure('logPrettyRequest', true);
    karate.configure('logPrettyResponse', true);

    var config =
        {
            baseURL: '',
            testData: ''
        }

    let env = karate.env;
    karate.log('karate.env: ', env);

    if (env.toLowerCase() == 'stratus') {
        config.baseURL = 'https://apipp.blackhawknetwork.com';
        config.testData = 'classpath:payloads/stratus/'
    } else if (env.toLowerCase() == 'fog') {
        config.baseURL = 'https://apiqa.blackhawknetwork.com';
        config.testData = 'classpath:payloads/fog/'
    }

    return config;
}