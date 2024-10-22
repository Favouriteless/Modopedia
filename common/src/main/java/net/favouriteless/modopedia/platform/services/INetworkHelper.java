package net.favouriteless.modopedia.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHelper {

    /**
     * Attempt to send a packet to a specific player FROM the server.
     *
     * @param packet The {@link CustomPacketPayload} to be sent.
     * @param player The {@link ServerPlayer} receiving the packet.
     */
    void sendToPlayer(CustomPacketPayload packet, ServerPlayer player);

    /**
     * Attempt to send a packet to all players FROM the server.
     *
     * @param packet The {@link CustomPacketPayload} to be sent.
     * @param server Access to {@link MinecraftServer} for Fabric to grab players from.
     */
    void sendToAllPlayers(CustomPacketPayload packet, MinecraftServer server);

    /**
     * Attempt to send a packet to the server FROM a client.
     *
     * @param packet The {@link CustomPacketPayload} to be sent.
     */
    void sendToServer(CustomPacketPayload packet);

}
