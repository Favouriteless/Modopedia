package net.favouriteless.modopedia.platform.services;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoNetworkHelper implements INetworkHelper {

    @Override
    public void sendToPlayer(CustomPacketPayload packet, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    @Override
    public void sendToAllPlayers(CustomPacketPayload packet, MinecraftServer server) {
        PacketDistributor.sendToAllPlayers(packet);
    }

    @Override
    public void sendToServer(CustomPacketPayload packet) {
        PacketDistributor.sendToServer(packet);
    }

}
