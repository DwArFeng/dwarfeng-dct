package com.dwarfeng.dct.api.integration.springtelqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dct.stack.bean.dto.FastJsonFlatData;
import com.dwarfeng.dct.stack.bean.dto.FlatData;
import com.dwarfeng.dct.stack.bean.dto.GeneralData;
import com.dwarfeng.dct.stack.service.DataCodingQosService;
import com.dwarfeng.dct.stack.struct.Data;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
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
import java.util.Date;
import java.util.List;

/**
 * 数据编码 Telqos 指令。
 *
 * <p>
 * 该指令提供数据编码 QoS 服务的命令行入口，覆盖处理器枚举、数据编码、数据解码能力。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@TelqosCommand
public class DataCodingCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "dcoding";

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
    private static final String COMMAND_SUB_OPTION_POINT_LONG_ID = "pli";
    private static final String COMMAND_SUB_OPTION_POINT_LONG_ID_LONG_OPT = "point-long-id";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_VALUE = "v";
    private static final String COMMAND_SUB_OPTION_VALUE_LONG_OPT = "value";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_HAPPENED_DATE = "hd";
    private static final String COMMAND_SUB_OPTION_HAPPENED_DATE_LONG_OPT = "happened-date";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET = "hdno";
    private static final String COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET_LONG_OPT = "happened-date-nano-offset";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_STRING = "s";
    private static final String COMMAND_SUB_OPTION_STRING_LONG_OPT = "string";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_SUB_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    private final DataCodingQosService dataCodingQosService;

    public DataCodingCommand(DataCodingQosService dataCodingQosService) {
        super(IDENTITY);
        this.dataCodingQosService = dataCodingQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "数据编码 QoS 服务";
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
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_POINT_LONG_ID) + " point-long-id] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_VALUE) + " value] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HAPPENED_DATE) + " happened-date] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET) +
                        " happened-date-nano-offset]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_DECODE) +
                        " [" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_HANDLER_NAME) + " handler-name] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file] " +
                        "[" + CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_STRING) + " string]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();

        list.add(
                Option.builder(COMMAND_OPTION_LIST_HANDLERS).longOpt(COMMAND_OPTION_LIST_HANDLERS_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("列出所有可用的数据编码处理器").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_ENCODE).longOpt(COMMAND_OPTION_ENCODE_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("将数据编码为文本").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_DECODE).longOpt(COMMAND_OPTION_DECODE_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("将文本解码为数据").build()
        );

        list.add(
                Option.builder(COMMAND_SUB_OPTION_HANDLER_NAME).longOpt(COMMAND_SUB_OPTION_HANDLER_NAME_LONG_OPT)
                        .hasArg(true).type(String.class).desc("数据编码处理器名称").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_POINT_LONG_ID).longOpt(COMMAND_SUB_OPTION_POINT_LONG_ID_LONG_OPT)
                        .hasArg(true).type(String.class).desc("数据点长整型 ID").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_VALUE).longOpt(COMMAND_SUB_OPTION_VALUE_LONG_OPT).hasArg(true)
                        .type(String.class).desc("数据值").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_HAPPENED_DATE).longOpt(COMMAND_SUB_OPTION_HAPPENED_DATE_LONG_OPT)
                        .hasArg(true).type(String.class).desc("发生时间毫秒时间戳").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET)
                        .longOpt(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET_LONG_OPT).hasArg(true).type(String.class)
                        .desc("发生时间对应毫秒中的纳秒偏移").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_STRING).longOpt(COMMAND_SUB_OPTION_STRING_LONG_OPT).hasArg(true)
                        .type(String.class).desc("数据编码文本").build()
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
        List<String> handlerNames = dataCodingQosService.listHandlerNames();
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
        Data data = parseDataForEncode(context, cmd);
        String encoded = dataCodingQosService.encode(handlerName, data);
        context.sendMessage("处理器名称: " + normalizeHandlerNameForOutput(handlerName));
        context.sendMessage("编码文本:");
        context.sendMessage(encoded);
    }

    private void handleDecode(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        String handlerName = parseHandlerName(context, cmd);
        String string = parseStringForDecode(context, cmd);
        Data data = dataCodingQosService.decode(handlerName, string);
        context.sendMessage("处理器名称: " + normalizeHandlerNameForOutput(handlerName));
        context.sendMessage("解码结果:");
        context.sendMessage("  pointKey: " + data.getPointKey());
        context.sendMessage("  value: " + data.getValue());
        context.sendMessage("  happenedDate: " + data.getHappenedDate());
        context.sendMessage("  happenedDateNanoOffset: " + data.getHappenedDateNanoOffset());
    }

    private Data parseDataForEncode(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            String pathText = requireJsonFilePath(cmd);
            String json = readTextFile(pathText, true);
            try {
                FastJsonFlatData fastJsonFlatData = JSON.parseObject(json, FastJsonFlatData.class);
                FlatData flatData = FastJsonFlatData.toStackBean(fastJsonFlatData);
                if (flatData == null) {
                    throw new IllegalArgumentException("JSON 文件内容无法解析为数据: " + pathText);
                }
                return new GeneralData(
                        flatData.getPointKey(),
                        flatData.getValue(),
                        flatData.getHappenedDate(),
                        flatData.getHappenedDateNanoOffset()
                );
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new IllegalArgumentException("JSON 文件内容无法解析为数据: " + pathText, e);
            }
        }

        long pointLongId = parsePointLongId(context, cmd);
        String value = parseValue(context, cmd);
        Date happenedDate = parseHappenedDate(context, cmd);
        int nanoOffset = parseHappenedDateNanoOffset(cmd);
        return new GeneralData(new LongIdKey(pointLongId), value, happenedDate, nanoOffset);
    }

    @SuppressWarnings("DuplicatedCode")
    private String parseStringForDecode(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            String pathText = requireJsonFilePath(cmd);
            return readTextFile(pathText, false);
        }
        if (cmd.hasOption(COMMAND_SUB_OPTION_STRING)) {
            String value = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_STRING));
            if (StringUtils.isNotEmpty(value)) {
                return value;
            }
        }
        context.sendMessage("请输入数据编码文本:");
        String value = StringUtils.trimToNull(context.receiveMessage());
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("数据编码文本不能为空");
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

        List<String> handlerNames = dataCodingQosService.listHandlerNames();
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

    private long parsePointLongId(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_POINT_LONG_ID)) {
            String raw = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_POINT_LONG_ID));
            if (StringUtils.isNotEmpty(raw)) {
                return parseLongParameter("point-long-id", raw);
            }
        }
        context.sendMessage("请输入数据点长整型 ID:");
        String raw = StringUtils.trimToNull(context.receiveMessage());
        if (StringUtils.isEmpty(raw)) {
            throw new IllegalArgumentException("数据点长整型 ID 不能为空");
        }
        return parseLongParameter("point-long-id", raw);
    }

    private String parseValue(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_VALUE)) {
            String value = cmd.getOptionValue(COMMAND_SUB_OPTION_VALUE);
            if (value != null && StringUtils.isNotEmpty(StringUtils.trimToNull(value))) {
                return value;
            }
        }
        context.sendMessage("请输入数据值:");
        String value = context.receiveMessage();
        if (StringUtils.isEmpty(StringUtils.trimToNull(value))) {
            throw new IllegalArgumentException("数据值不能为空");
        }
        return value;
    }

    private Date parseHappenedDate(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_HAPPENED_DATE)) {
            String raw = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_HAPPENED_DATE));
            if (StringUtils.isNotEmpty(raw)) {
                long ms = parseLongParameter("happened-date", raw);
                return new Date(ms);
            }
        }
        context.sendMessage("请输入发生时间毫秒时间戳:");
        String raw = StringUtils.trimToNull(context.receiveMessage());
        if (StringUtils.isEmpty(raw)) {
            throw new IllegalArgumentException("发生时间毫秒时间戳不能为空");
        }
        long ms = parseLongParameter("happened-date", raw);
        return new Date(ms);
    }

    private int parseHappenedDateNanoOffset(CommandLine cmd) throws Exception {
        if (!cmd.hasOption(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET)) {
            return 0;
        }
        String raw = StringUtils.trimToNull(cmd.getOptionValue(COMMAND_SUB_OPTION_HAPPENED_DATE_NANO_OFFSET));
        if (StringUtils.isEmpty(raw)) {
            return 0;
        }
        return parseIntParameter("happened-date-nano-offset", raw);
    }

    private long parseLongParameter(String name, String raw) {
        try {
            return Long.parseLong(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("参数 " + name + " 无法解析为整数: " + raw, e);
        }
    }

    // 为了代码的一致性和可扩展性，此处不做简化。
    @SuppressWarnings({"SameParameterValue", "RedundantThrows"})
    private int parseIntParameter(String name, String raw) throws Exception {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("参数 " + name + " 无法解析为整数: " + raw, e);
        }
    }

    private String normalizeHandlerNameForOutput(@Nullable String handlerName) {
        return StringUtils.defaultIfBlank(handlerName, "<default>");
    }
}
