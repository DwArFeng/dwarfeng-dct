package com.dwarfeng.dct.api.integration.springtelqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dct.stack.service.ValueCodingQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 值编码 Telqos 指令。
 *
 * <p>
 * 该指令提供值编码 QoS 服务的命令行入口，覆盖处理器枚举、对象编码、值文本解码能力。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@TelqosCommand
public class ValueCodingCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "vcoding";

    // region 指令选项

    private static final String COMMAND_OPTION_LIST_HANDLERS = "lh";
    private static final String COMMAND_OPTION_LIST_HANDLERS_LONG_OPT = "list-handlers";
    private static final String COMMAND_OPTION_ENCODE = "en";
    private static final String COMMAND_OPTION_ENCODE_LONG_OPT = "encode";
    private static final String COMMAND_OPTION_DECODE = "de";
    private static final String COMMAND_OPTION_DECODE_LONG_OPT = "decode";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LIST_HANDLERS,
            COMMAND_OPTION_ENCODE,
            COMMAND_OPTION_DECODE
    };

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_HANDLER_NAME = "hn";
    private static final String COMMAND_SUB_OPTION_HANDLER_NAME_LONG_OPT = "handler-name";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_TARGET_CLASS = "tc";
    private static final String COMMAND_SUB_OPTION_TARGET_CLASS_LONG_OPT = "target-class";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_TEXT = "t";
    private static final String COMMAND_SUB_OPTION_TEXT_LONG_OPT = "text";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final ValueCodingQosService valueCodingQosService;

    public ValueCodingCommand(ValueCodingQosService valueCodingQosService) {
        super(IDENTITY);
        this.valueCodingQosService = valueCodingQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "值编码 QoS 服务";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LIST_HANDLERS),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_ENCODE) +
                        " [" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HANDLER_NAME) + " handler-name] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_TARGET_CLASS) + " target-class] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_DECODE) +
                        " [" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HANDLER_NAME) + " handler-name] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_TEXT) + " text]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();

        list.add(
                Option.builder(COMMAND_OPTION_LIST_HANDLERS).longOpt(COMMAND_OPTION_LIST_HANDLERS_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("列出所有可用的值编码处理器").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_ENCODE).longOpt(COMMAND_OPTION_ENCODE_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("将目标对象编码为值文本").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_DECODE).longOpt(COMMAND_OPTION_DECODE_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("将值文本解码为对象").build()
        );

        list.add(
                Option.builder(COMMAND_SUB_OPTION_HANDLER_NAME).longOpt(COMMAND_SUB_OPTION_HANDLER_NAME_LONG_OPT)
                        .hasArg(true).type(String.class).desc("值编码处理器名称").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_TARGET_CLASS).longOpt(COMMAND_SUB_OPTION_TARGET_CLASS_LONG_OPT)
                        .hasArg(true).type(String.class).desc("目标对象类的全限定名").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_TEXT).longOpt(COMMAND_SUB_OPTION_TEXT_LONG_OPT).hasArg(true)
                        .type(String.class).desc("值编码文本").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON_FILE).longOpt(COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT)
                        .hasArg(true).type(String.class).desc("JSON 文件路径").build()
        );

        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }

        switch (pair.getLeft()) {
            case COMMAND_OPTION_LIST_HANDLERS:
                handleListHandlers(context);
                break;
            case COMMAND_OPTION_ENCODE:
                handleEncode(context, cmd);
                break;
            case COMMAND_OPTION_DECODE:
                handleDecode(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleListHandlers(CommandExecutor.Context context) throws Exception {
        List<String> handlerNames = valueCodingQosService.listHandlerNames();
        context.sendMessage("可用的处理器名称:");
        if (handlerNames.isEmpty()) {
            context.sendMessage("  (Empty)");
            return;
        }
        for (int i = 0; i < handlerNames.size(); i++) {
            String handlerName = handlerNames.get(i);
            context.sendMessage(String.format("  %3d: %s", i + 1, handlerName));
        }
    }

    private void handleEncode(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        String handlerName = parseHandlerName(context, cmd);
        Object target = parseTargetForEncode(context, cmd);
        String encoded = valueCodingQosService.encode(handlerName, target);
        context.sendMessage("处理器名称: " + normalizeHandlerNameForOutput(handlerName));
        context.sendMessage("值编码文本:");
        context.sendMessage(encoded);
    }

    private void handleDecode(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        String handlerName = parseHandlerName(context, cmd);
        String text = parseTextForDecode(context, cmd);
        Object result = valueCodingQosService.decode(handlerName, text);
        context.sendMessage("处理器名称: " + normalizeHandlerNameForOutput(handlerName));
        context.sendMessage("解码结果类型: " + formatResultType(result));
        context.sendMessage("解码结果文本: " + formatResultText(result));
    }

    private Object parseTargetForEncode(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        String targetClassName = parseTargetClassName(context, cmd);
        String pathText = requireJsonFilePath(cmd);
        String json = readTextFile(pathText, true);
        Class<?> targetClass;
        try {
            targetClass = Class.forName(targetClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("目标类不存在: " + targetClassName, e);
        }
        try {
            return JSON.parseObject(json, targetClass);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "JSON 文件内容无法反序列化为目标类: " + pathText + ", target-class=" + targetClassName, e
            );
        }
    }

    private String parseTargetClassName(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_TARGET_CLASS)) {
            String targetClassName = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_TARGET_CLASS));
            if (StringUtils.isNotEmpty(targetClassName)) {
                return targetClassName;
            }
        }
        context.sendMessage("请输入目标对象类的全限定名:");
        String targetClassName = StringUtils.trimToNull(context.receiveMessage());
        if (StringUtils.isEmpty(targetClassName)) {
            throw new IllegalArgumentException("目标对象类的全限定名不能为空");
        }
        return targetClassName;
    }

    @SuppressWarnings("DuplicatedCode")
    private String parseTextForDecode(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            String pathText = requireJsonFilePath(cmd);
            return readTextFile(pathText, false);
        }
        if (cmd.hasOption(COMMAND_SUB_OPTION_TEXT)) {
            String value = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_TEXT));
            if (StringUtils.isNotEmpty(value)) {
                return value;
            }
        }
        context.sendMessage("请输入值编码文本:");
        String value = StringUtils.trimToNull(context.receiveMessage());
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("值编码文本不能为空");
        }
        return value;
    }

    private String requireJsonFilePath(CommandLine cmd) {
        String pathText = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_JSON_FILE));
        if (StringUtils.isEmpty(pathText)) {
            throw new IllegalArgumentException("json-file 路径不能为空");
        }
        return pathText;
    }

    @SuppressWarnings("DuplicatedCode")
    private String readTextFile(String pathText, boolean trimContent) throws Exception {
        Path path = Paths.get(pathText);
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("文件不存在或不是普通文件: " + pathText);
        }
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("文件不可读: " + pathText);
        }
        byte[] bytes = Files.readAllBytes(path);
        String text = new String(bytes, StandardCharsets.UTF_8);
        return trimContent ? text.trim() : text;
    }

    @SuppressWarnings("DuplicatedCode")
    @Nullable
    private String parseHandlerName(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_HANDLER_NAME)) {
            return StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_HANDLER_NAME));
        }

        List<String> handlerNames = valueCodingQosService.listHandlerNames();
        if (handlerNames.size() <= 1) {
            return null;
        }

        context.sendMessage("可用的处理器名称:");
        for (int i = 0; i < handlerNames.size(); i++) {
            context.sendMessage(String.format("  %3d: %s", i + 1, handlerNames.get(i)));
        }
        context.sendMessage("请输入处理器名称:");
        return StringUtils.trimToNull(context.receiveMessage());
    }

    private String formatResultType(@Nullable Object result) {
        if (result == null) {
            return "null";
        }
        return result.getClass().getName();
    }

    private String formatResultText(@Nullable Object result) {
        if (result == null) {
            return "null";
        }
        if (result instanceof byte[]) {
            byte[] bytes = (byte[]) result;
            return "length=" + bytes.length + ", base64=" + Base64.getEncoder().encodeToString(bytes);
        }
        return String.valueOf(result);
    }

    private String normalizeHandlerNameForOutput(@Nullable String handlerName) {
        return StringUtils.defaultIfBlank(handlerName, "<default>");
    }
}
