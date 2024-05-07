package com.project.compagnoserver.controller;

import com.project.compagnoserver.domain.Parsing.Parsing;
import com.project.compagnoserver.service.ParsingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/compagno/public/content")
@RequiredArgsConstructor
@Slf4j
public class ParsingController {

    @Autowired
    private ParsingService service;

    @GetMapping("/api")
    public ResponseEntity<JSONObject> fetch() throws Exception {
        int no = 0;
        HttpURLConnection conn = null;
        InputStream stream = null;
        JSONObject result = null;

        for (int n = 1; n <= 8; n++) {
            no = n;
            URL url = new URL("https://api.odcloud.kr/api/15111389/v1/uddi:41944402-8249-4e45-9e9d-a52d0a7db1cc" + "?serviceKey="
                    + "tnwsvSMjoerFvcPcRFCYR4%2BmsallmYrsSDJ7i%2FfXLkoBC4v%2BPHzMyielKVwBooRmzRhNueQJz1Hk6bsa9b9rqw%3D%3D"
                    + "&page=" + no + "&perPage=3000" + "&returnType=JSON");

            conn = (HttpURLConnection) url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToString(stream);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + conn.getResponseCode());
        }
        return conn.getInputStream();
    }

    private JSONObject readStreamToString(InputStream stream) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

            String readLine;
            StringBuffer responseBuffer = new StringBuffer();

            while ((readLine = br.readLine()) != null) {
                responseBuffer.append(readLine);
                result.append(readLine + "\n\r");
            }

            // JSON 파싱 시작
            String responseData = responseBuffer.toString();
            JSONObject jsonResponse = new JSONObject(responseData);
            JSONArray parsing = jsonResponse.getJSONArray("data");

            for (int i = 0; i < parsing.length(); i++) {
                JSONObject vo = parsing.getJSONObject(i);
                String name = vo.getString("시설명");
                String mainCate = vo.getString("카테고리2");
                String subCate = vo.getString("카테고리3");
                String mainregCate = vo.getString("시도 명칭");
                String subregCate = vo.optString("시군구 명칭", "-");
                String latitude = vo.getString("위도");
                String longtitude = vo.getString("경도");
                String roadAddr = vo.optString("도로명주소", "-");
                String addr = vo.getString("지번주소");
                String phone = vo.getString("전화번호");
                String url = vo.getString("홈페이지");
                String parking = vo.getString("주차 가능여부");
                String fee = vo.getString("입장(이용료)가격 정보");
                String holiday = vo.getString("휴무일");
                String operatingHours = vo.getString("운영시간");
                Parsing loca = new Parsing();

                loca.setName(name);
                loca.setMainCate(mainCate);
                loca.setSubCate(subCate);
                loca.setMainregCate(mainregCate);
                loca.setSubregCate(subregCate);
                loca.setLatitude(latitude);
                loca.setLongtitude(longtitude);
                loca.setRoadAddr(roadAddr);
                loca.setAddr(addr);
                loca.setPhone(phone);
                loca.setUrl(url);
                loca.setParking(parking);
                loca.setFee(fee);
                loca.setHoliday(holiday);
                loca.setOperatingHours(operatingHours);

                service.create(loca);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
