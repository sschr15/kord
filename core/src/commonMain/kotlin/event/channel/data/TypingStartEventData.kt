package dev.kord.core.event.channel.data

import dev.kord.common.entity.DiscordGuildMember
import dev.kord.common.entity.DiscordTyping
import dev.kord.common.entity.Snowflake
import dev.kord.common.entity.optional.Optional
import dev.kord.common.entity.optional.OptionalSnowflake
import dev.kord.common.serialization.InstantInEpochSeconds
import kotlinx.serialization.Serializable

@Serializable
public data class TypingStartEventData(
    val channelId: Snowflake,
    val guildId: OptionalSnowflake = OptionalSnowflake.Missing,
    val userId: Snowflake,
    val timestamp: InstantInEpochSeconds,
    val member: Optional<DiscordGuildMember> = Optional.Missing()
) {
    public companion object {
        public fun from(entity: DiscordTyping): TypingStartEventData = with(entity) {
            TypingStartEventData(channelId, guildId, userId, timestamp, member)
        }
    }
}
