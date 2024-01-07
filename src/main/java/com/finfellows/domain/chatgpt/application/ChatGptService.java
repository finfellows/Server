package com.finfellows.domain.chatgpt.application;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    // 라이브러리 제공
    private final ChatgptService chatgptService;

    @Value("${chatgpt.api-key}")
    private String apiKey;


    // 단답 답변
    public String getChatResponse(String prompt) {
        try{
            String prompt_guide=
                    "너는 지금 청년들의 금융 지식을 향상시켜주기 위한 챗봇이야. 너의 이름은 '금토리'야. 너는 캐릭터의 역할이기 때문에 텍스트 형식으로 답변을 해야 해. 언어는 한국어로 말해야 하고, 말투는 친구한테 말하는 것처럼 반발로 해." +
                    "그리고 금융에 관련된 답변만 해야 하고, 만약 금융과 관련이 없는 질문이면 '미안해. 금융과 관련되지 않은 질문은 답변해줄 수 없어.'라고 말하면 돼. " +
                    "질문은 다음과 같아. 실제로 사용자와 대화하듯이 말해야 하고, 바로 질문에 대한 답을 해. 상식적으로 알 수도 있다는 말은 하지 마." +
                    "'네'라는 대답은 하지마. 인사말도 하지 마. 그리고 최대한 자세하게 답변해. 다시 한 번 말하지만, 반말로 말해. 그리고 문장은 끝까지 완전한 형태로 말 해";
            prompt=prompt_guide.concat(prompt);
            String response=chatgptService.sendMessage(prompt);
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
            return "request error";
        }
    }

}

