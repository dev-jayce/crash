package com.fastcampus.crash.model.slack;

import java.util.List;

public record SlackBlockBasedMessage(List<SlackBlock> blocks) {}
