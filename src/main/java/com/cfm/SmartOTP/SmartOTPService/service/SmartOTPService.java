package com.cfm.SmartOTP.SmartOTPService.service;

import com.cfm.SmartOTP.SmartOTPService.component.RSACryptosystem;
import com.cfm.SmartOTP.SmartOTPService.component.TOTPAlgorithm;
import com.cfm.SmartOTP.SmartOTPService.dto.CFMTransactionDto;
import com.cfm.SmartOTP.SmartOTPService.entity.CFMApiAccount;
import com.cfm.SmartOTP.SmartOTPService.entity.CFMTransaction;
import com.cfm.SmartOTP.SmartOTPService.helper.JWTHepler;
import com.cfm.SmartOTP.SmartOTPService.model.CreateJWTTokenModel;
import com.cfm.SmartOTP.SmartOTPService.model.CreateSecretKeyModel;
import com.cfm.SmartOTP.SmartOTPService.model.Response;
import com.cfm.SmartOTP.SmartOTPService.model.VerifyOTPModel;
import com.cfm.SmartOTP.SmartOTPService.repository.CFMApiAccontRepository;
import com.cfm.SmartOTP.SmartOTPService.repository.CFMTransactionRepository;
import com.cfm.SmartOTP.SmartOTPService.util.Constants;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmartOTPService {
    private final CFMTransactionRepository cfmTransactionRepository;
    private final CFMApiAccontRepository cfmApiAccontRepository;
    private final RSACryptosystem rsaCryptosystem;
    private final TOTPAlgorithm totpAlgorithm;
    private final JWTHepler jwtHepler;
    private final ModelMapper modelMapper;
    private final Environment environment;

    public SmartOTPService(CFMTransactionRepository cfmTransactionRepository, CFMApiAccontRepository cfmApiAccontRepository, RSACryptosystem rsaCryptosystem, TOTPAlgorithm totpAlgorithm, JWTHepler jwtHepler, ModelMapper modelMapper, Environment environment) {
        this.cfmTransactionRepository = cfmTransactionRepository;
        this.cfmApiAccontRepository = cfmApiAccontRepository;
        this.rsaCryptosystem = rsaCryptosystem;
        this.totpAlgorithm = totpAlgorithm;
        this.jwtHepler = jwtHepler;
        this.modelMapper = modelMapper;
        this.environment = environment;
    }

    public Response getSecrectKey(CreateSecretKeyModel inputData) {
        Response result = new Response();

        //Check xem ???ng d???ng call c?? ???????c c???p s??? d???ng SmartOTP kh??ng
        if (this.AccessVerify(inputData.getAppCode(), inputData.getAppKey())) {
            String secretKey = "";
            boolean isExist = false;

            // Check xem user c???a app n??y ???? c?? secret key trong db ch??a
            List<CFMTransaction> cfmTransactionList = cfmTransactionRepository.findByAppcodeAndUserid(inputData.getAppCode(), inputData.getUserId());

            if (cfmTransactionList != null && cfmTransactionList.size() > 0) {
                // N???u ???? t???n t???i th?? g??n l???i secretkey c??
                // V?? secret key ??? ????y ???? m?? h??a n??n ph???i gi???i m?? ???? nh??
                byte[] prvKeySOTPByteArr = Base64.getDecoder().decode(Constants.RSA_KEY.PRIVATE_KEY);
                secretKey = rsaCryptosystem.Decryption(prvKeySOTPByteArr, cfmTransactionList.get(0).getSecretkey());

                isExist = true;
            } else {
                secretKey = this.generateSecretKey();
            }

            //M?? h??a secret key v??? Base32
            String encodedSecret = new Base32().encodeToString(secretKey.getBytes());

            //Get Pucblic key c???a Client ??? param
            byte[] pubKeyClientByteArr = Base64.getDecoder().decode(inputData.getPublicKey());

            //M?? h??a encodedSecret theo RSA ????? tr??? l???i Client
            String secretKeyEncoder_Client = rsaCryptosystem.Encrpytion(pubKeyClientByteArr, encodedSecret);

            //T???o token ????? x??c th???c l???n sau
            // V?? s??? d???ng m?? h??a RSA n??n kh??ng c???n JWT n???a ????u
            // String jwtKey = environment.getProperty("JWT_KEY");
            // String jwtToken = jwtHepler.createJWT(jwtKey, secretKey, inputData.getUserId());

            //L??u th??ng tin SOTP_TRANSACTION khi user ch??a c?? trong DB h??? th???ng
            if (!isExist) {
                //M?? h??a SecretKey ????? b???o m???t
                byte[] pubKeySOTPByteArr = Base64.getDecoder().decode(Constants.RSA_KEY.PUBLIC_KEY);
                String secretKeyEnCodeRSA = rsaCryptosystem.Encrpytion(pubKeySOTPByteArr, secretKey);

                //L??u ??? ????y th??i nh?? g??i
                CFMTransaction cfmTransaction = new CFMTransaction();

                cfmTransaction.setBrandname(inputData.getAppCode());
                cfmTransaction.setType(1);
                cfmTransaction.setUserid(inputData.getUserId());
                cfmTransaction.setContent("Create Secret Key");
                cfmTransaction.setRegioncode(inputData.getRegionCode());
                cfmTransaction.setAppcode(inputData.getAppCode());
                cfmTransaction.setExtrainfo(inputData.getExtraInfo());
                cfmTransaction.setStatusid(BigInteger.ONE);
                cfmTransaction.setCreatedby(inputData.getRegionCode());
                cfmTransaction.setSecretkey(secretKeyEnCodeRSA);
                cfmTransaction.setRemark("CFM");

                this.cfmTransactionRepository.save(cfmTransaction);
            }

            result.setErrorCode("0");
            //result.setJwt(jwtToken);
            result.setSecretkey(secretKeyEncoder_Client);
            result.setMessage(Constants.RESPONSE_MESSAGE.SUCCESS_CODE_TOTP_CREATE);
        } else {
            result.setErrorCode("1");
            result.setMessage(Constants.RESPONSE_MESSAGE.ERROR_CODE_JWT_AUTH);
        }

        return result;
    }

    public Response getJWTByUserId(CreateJWTTokenModel inputData) {
        Response result = new Response();

        //Check xem ???ng d???ng call c?? ???????c c???p s??? d???ng SmartOTP kh??ng
        if (this.AccessVerify(inputData.getAppCode(), inputData.getAppKey())) {
            // Check xem user c???a app n??y ???? c?? secret key trong db ch??a
            List<CFMTransaction> cfmTransactionList = cfmTransactionRepository.findByAppcodeAndUserid(inputData.getAppCode(), inputData.getUserId());
            if (cfmTransactionList != null && cfmTransactionList.size() > 0) {
                // V?? secret key ??? ????y ???? m?? h??a n??n ph???i gi???i m?? ???? nh??
                byte[] prvKeySOTPByteArr = Base64.getDecoder().decode(Constants.RSA_KEY.PRIVATE_KEY);
                String secretKey = rsaCryptosystem.Decryption(prvKeySOTPByteArr, cfmTransactionList.get(0).getSecretkey());

                //T???o token ????? x??c th???c l???n sau
                String jwtKey = environment.getProperty("JWT_KEY");
                String jwtToken = JWTHepler.createJWT(jwtKey, secretKey, inputData.getUserId());

                result.setErrorCode("0");
                result.setJwt(jwtToken);
                result.setMessage(Constants.RESPONSE_MESSAGE.SUCCESS_CODE_JWT_AUTH);
            } else {
                result.setErrorCode("1");
                result.setMessage(Constants.RESPONSE_MESSAGE.ERROR_USER_TOTP_AUTH);
            }
        } else {
            result.setErrorCode("1");
            result.setMessage(Constants.RESPONSE_MESSAGE.ERROR_CODE_JWT_AUTH);
        }

        return result;
    }

    public Response verifyOTP(VerifyOTPModel inputData) {
        Response result = new Response();

        //Check xem ???ng d???ng call c?? ???????c c???p s??? d???ng SmartOTP kh??ng
        if (this.AccessVerify(inputData.getAppCode(), inputData.getAppKey())) {
            // Check xem user c???a app n??y ???? c?? secret key trong db ch??a
            List<CFMTransaction> cfmTransactionList = cfmTransactionRepository.findByAppcodeAndUserid(inputData.getAppCode(), inputData.getUserId());
            if (cfmTransactionList != null && cfmTransactionList.size() > 0) {
                // V?? secret key ??? ????y ???? m?? h??a n??n ph???i gi???i m?? ???? nh??
                byte[] prvKeySOTPByteArr = Base64.getDecoder().decode(Constants.RSA_KEY.PRIVATE_KEY);
                String secretKey = rsaCryptosystem.Decryption(prvKeySOTPByteArr, cfmTransactionList.get(0).getSecretkey());

                //T???o token ????? x??c th???c l???n sau
                String jwtKey = environment.getProperty("JWT_KEY");
                String jwtToken = JWTHepler.createJWT(jwtKey, secretKey, inputData.getUserId());

                if (jwtToken.equals(inputData.getJwtToken())) {
                    String sizeTOTP = environment.getProperty("TOTP_SIZE");

                    if (this.verifyCode(inputData.getOtpCode(), secretKey, sizeTOTP)) {
                        result.setErrorCode("0");
                        result.setMessage(Constants.RESPONSE_MESSAGE.SUCCESS_CODE_TOTP_AUTH);
                    } else {
                        result.setErrorCode("1");
                        result.setMessage(Constants.RESPONSE_MESSAGE.ERROR_CODE_TOTP_AUTH);
                    }
                } else {
                    result.setErrorCode("1");
                    result.setMessage(Constants.RESPONSE_MESSAGE.ERROR_CODE_JWT_VERIRY);
                }
            } else {
                result.setErrorCode("1");
                result.setMessage(Constants.RESPONSE_MESSAGE.ERROR_USER_JWT_AUTH);
            }
        } else {
            result.setErrorCode("1");
            result.setMessage(Constants.RESPONSE_MESSAGE.ERROR_CODE_JWT_AUTH);
        }

        return result;
    }

    public List<CFMTransactionDto> getAllTransaction() {
        List<CFMTransactionDto> result = new ArrayList<>();
        try {
            List<CFMTransaction> cfmTransactions = this.cfmTransactionRepository.findAll();

            result = this.mapList(cfmTransactions, CFMTransactionDto.class);

        } catch (Exception ex) {
            return null;
        }
        return result;
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public Boolean AccessVerify(String appCode, String appkey) {
        //N???u kh??ng c???n x??c th???c quy???n g???i API c???a ???ng d???ng th?? ?????t bi???n m??i tr?????ng b???ng True
        String passAcc = environment.getProperty("PASS.ACC");

        if (passAcc != null && Boolean.parseBoolean(passAcc)) {
            return true;
        }

        //Get all SOTP_APIAccount
        List<CFMApiAccount> cfmApiAccounts = cfmApiAccontRepository.findByAppcodeAndAppkeyAndIsactive(appCode, appkey, 1);
        if (cfmApiAccounts != null && cfmApiAccounts.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String generateSecretKey() {
        return RandomStringUtils.random(Constants.SECRET_SIZE, true, true).toUpperCase();
    }

    public boolean verifyCode(String totpCode, String secret, String totpsize) {
        // Getting current timestamp representing 30 seconds time frame
        long timeFrame = System.currentTimeMillis() / 1000L / 30;

        //L???y ra 3 m?? OTP c???a 3 kho???ng th???i gian ????? tr??nh tr?????ng h???p b??? tr??? khi t???o v?? call API
        String totpCodeBySecret_bf = generateTotpBySecretToTime(secret, totpsize, timeFrame - 1);
        String totpCodeBySecret = generateTotpBySecretToTime(secret, totpsize, timeFrame);
        String totpCodeBySecret_af = generateTotpBySecretToTime(secret, totpsize, timeFrame + 1);

        if (totpCodeBySecret_bf.equals(totpCode) || totpCodeBySecret.equals(totpCode) || totpCodeBySecret_af.equals(totpCode)) {
            return true;
        } else {
            return false;
        }
    }

    private String generateTotpBySecretToTime(String secret, String totpsize, long timeFrame) {

        // Encoding time frame value to HEX string - requred by TOTP generator which is used here.
        String timeEncoded = Long.toHexString(timeFrame);

        String totpCodeBySecret;
        try {
            // Encoding given secret string to HEX string - requred by TOTP generator which is used here.
            char[] secretEncoded = (char[]) new Hex().encode(secret);

            // Generating TOTP by given time and secret - using TOTP algorithm implementation provided by IETF.
            totpCodeBySecret = totpAlgorithm.generateTOTP(String.copyValueOf(secretEncoded), timeEncoded, totpsize);
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
        return totpCodeBySecret;
    }
}
