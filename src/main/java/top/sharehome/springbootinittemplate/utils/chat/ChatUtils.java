package top.sharehome.springbootinittemplate.utils.chat;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import groovy.util.logging.Slf4j;
import top.sharehome.springbootinittemplate.common.base.ReturnCode;
import top.sharehome.springbootinittemplate.config.bean.SpringContextHolder;
import top.sharehome.springbootinittemplate.exception.customize.CustomizeReturnException;

/**
 * AI对话工具类
 *
 * @author AntonyCheng
 * @since 2023/12/24 19:06:36
 */
@Slf4j
public class ChatUtils {

    /**
     * 封装好的AI
     */
    private static final YuCongMingClient AI_CLIENT = SpringContextHolder.getBean(YuCongMingClient.class);

    public static String doChat(long modelId, String message) {
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> response = AI_CLIENT.doChat(devChatRequest);
        if (response == null) {
            throw new CustomizeReturnException(ReturnCode.FAIL, "AI 响应异常");
        }
        return response.getData().getContent();
    }


}
